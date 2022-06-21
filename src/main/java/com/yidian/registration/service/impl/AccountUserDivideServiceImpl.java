package com.yidian.registration.service.impl;

import com.yidian.registration.dao.*;
import com.yidian.registration.entity.*;
import com.yidian.registration.enums.DivideCommissionTypeEnum;
import com.yidian.registration.enums.StatusEnum;
import com.yidian.registration.enums.UserCommissionTypeEnum;
import com.yidian.registration.enums.UserStatusEnum;
import com.yidian.registration.exception.BizException;
import com.yidian.registration.service.IAccountUserDivideService;
import com.yidian.registration.utils.DateBuilder;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.account.divide.AccountDivideDetailVo;
import com.yidian.registration.vo.account.divide.AccountDivideVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2022/6/19 23:06
 */
@Service
public class AccountUserDivideServiceImpl implements IAccountUserDivideService {

    Logger logger = LoggerFactory.getLogger(AccountUserDivideServiceImpl.class);

    @Resource
    private TConfigUserCommissionDao configUserCommissionDao;

    @Resource
    private TConfigHospitalDao configHospitalDao;

    @Resource
    private TConfigItemDao configItemDao;

    @Resource
    private TAccountRecordDao accountRecordDao;

    @Resource
    private TAccountUserDivideDao accountUserDivideDao;

    @Resource
    private TAccountUserDivideDetailDao accountUserDivideDetailDao;

    @Override
    public PageVo<List<AccountDivideVo>> getAccountDivideList(String settleDate, Long belongId, Integer pageNo, Integer pageSize) {
        logger.info("getAccountDivideList start, settleDate:{}, belongId:{}, pageNo:{}, pageSize:{}", settleDate, belongId, pageNo, pageSize);
        PageVo<List<AccountDivideVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(pageNo);
        pageVo.setPageSize(pageSize);
        //查询总量
        int total = accountUserDivideDao.selectListTotal(belongId, settleDate);
        if (total <= 0) {
            return pageVo;
        }
        pageVo.setCount(total);

        int index = (pageNo - 1) * pageSize;
        List<TAccountUserDivide> divideList = accountUserDivideDao.selectList(belongId, settleDate, index, pageSize);
        if (CollectionUtils.isEmpty(divideList)) {
            return pageVo;
        }
        List<AccountDivideVo> list = new ArrayList<>();
        for (TAccountUserDivide divide : divideList) {
            AccountDivideVo vo = entityToVo(divide);
            list.add(vo);
        }
        pageVo.setData(list);
        logger.info("getAccountDivideList end, settleDate:{}, belongId:{}, pageNo:{}, pageSize:{}, pageVo:{}", settleDate, belongId, pageNo, pageSize, pageVo);
        return pageVo;
    }

    private AccountDivideVo entityToVo(TAccountUserDivide divide) {
        AccountDivideVo vo = new AccountDivideVo();
        vo.setId(divide.getId());
        vo.setMonth(divide.getMonth());
        vo.setBelongId(divide.getBelongId());
        vo.setBelongName(divide.getBelongName());
        vo.setInvoiceTotalMoney(divide.getInvoiceTotalMoney());
        vo.setIncome(divide.getIncome());
        vo.setCommissionMoney(divide.getCommissionMoney());
        vo.setStatus(divide.getStatus());
        vo.setCreateTime(DateBuilder.formatDate(divide.getCreateTime(), DateBuilder.FORMAT_FULL));
        vo.setUpdateTime(DateBuilder.formatDate(divide.getUpdateTime(), DateBuilder.FORMAT_FULL));
        return vo;
    }

    private AccountDivideDetailVo entityDetailToVo(TAccountUserDivideDetail divideDetail) {
        AccountDivideDetailVo vo = new AccountDivideDetailVo();
        vo.setId(divideDetail.getId());
        vo.setBelongId(divideDetail.getBelongId());
        vo.setBelongName(divideDetail.getBelongName());
        vo.setHospitalId(divideDetail.getHospitalId());
        vo.setHospitalName(divideDetail.getHospitalName());
        vo.setItemId(divideDetail.getItemId());
        vo.setItemName(divideDetail.getItemName());
        vo.setInvoiceTotalMoney(divideDetail.getInvoiceTotalMoney());
        vo.setIncome(divideDetail.getIncome());
        vo.setCommission(divideDetail.getCommission());
        vo.setStatus(divideDetail.getStatus());
        vo.setCreateTime(DateBuilder.formatDate(divideDetail.getCreateTime(), DateBuilder.FORMAT_FULL));
        vo.setUpdateTime(DateBuilder.formatDate(divideDetail.getUpdateTime(), DateBuilder.FORMAT_FULL));
        return vo;
    }

    @Override
    public AccountDivideVo getAccountDivideDeail(Long did) {
        TAccountUserDivide userDivide = accountUserDivideDao.selectInfoById(did);
        if (Objects.isNull(userDivide)) {
            return null;
        }
        //查询分成数据
        AccountDivideVo accountDivideVo = entityToVo(userDivide);
        //项目开票收入
        List<AccountDivideDetailVo> detailList = getDivideItemDetail(did, DivideCommissionTypeEnum.MAIN.getType());
        accountDivideVo.setItemDivides(detailList);
        //是否是主类型
        TConfigUserCommission userCommission = configUserCommissionDao.selectInfoById(accountDivideVo.getBelongId());
        if (Objects.nonNull(userCommission) && UserCommissionTypeEnum.MAIN.getType().equals((int) userCommission.getType())) {
            //查询票据提成收入
            List<AccountDivideDetailVo> commissionDetailList = getDivideItemDetail(did, DivideCommissionTypeEnum.DEPUTY.getType());
            accountDivideVo.setItemDivides(detailList);
        }
        return accountDivideVo;
    }

    /**
     * 获取开票提成详情
     *
     * @param did
     * @param type
     * @return
     */
    private List<AccountDivideDetailVo> getDivideItemDetail(Long did, Integer type) {
        if (Objects.isNull(did) || Objects.isNull(type)) {
            return Collections.emptyList();
        }
        //查询详情
        List<TAccountUserDivideDetail> itemDivides = accountUserDivideDetailDao.selectDetailListByDid(did, type);
        if (CollectionUtils.isEmpty(itemDivides)) {
            return Collections.emptyList();
        }
        //项目开票收入
        List<AccountDivideDetailVo> detailList = new ArrayList<>();
        for (TAccountUserDivideDetail detail : itemDivides) {
            AccountDivideDetailVo detailVo = entityDetailToVo(detail);
            detailList.add(detailVo);
        }
        return detailList;
    }

    @Override
    public PageVo<List<AccountDivideDetailVo>> getItemDetail(Long did, Integer type) {
        PageVo<List<AccountDivideDetailVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(1);
        pageVo.setPageSize(100);
        List<AccountDivideDetailVo> divideDetailVos = getDivideItemDetail(did, type);
        pageVo.setData(divideDetailVos);
        pageVo.setCount(divideDetailVos.size());
        return pageVo;
    }

    @Override
    public boolean generateDivide(String settleDate, Long belongId) {
        logger.info("[generateDivide] start settleDate:{}, belongId:{}", settleDate, belongId);
        //检查当月是否存在账单
        List<TAccountUserDivide> divideList = accountUserDivideDao.selectInfoByMonth(belongId, settleDate);
        if (!CollectionUtils.isEmpty(divideList)) {
            throw new BizException(StatusEnum.NODATA_CODE.getCode(), "当月已存在有效分成账单");
        }
        //查找主分成人员
        List<TConfigUserCommission> userCommissionsMain = configUserCommissionDao.selectUserCommissionConfigList(null, UserCommissionTypeEnum.MAIN.getType());
        TConfigUserCommission commissionMain = null;
        if (CollectionUtils.isEmpty(userCommissionsMain)) {
            throw new BizException(StatusEnum.NODATA_CODE.getCode(), "缺少核心人员配置");
        }
        commissionMain = userCommissionsMain.get(0);
        //计算主分成人员的 开票收入
        Long mainDivideId = generateMainUserDivide(commissionMain, settleDate);
        //查询普通分成人员
        List<TConfigUserCommission> userCommissionsDeputy = configUserCommissionDao.selectUserCommissionConfigList(belongId, UserCommissionTypeEnum.DEPUTY.getType());
        if (CollectionUtils.isEmpty(userCommissionsDeputy)) {
            logger.info("[generateDivide] not query user commission, settleDate:{}, belongId:{}", settleDate, belongId);
            return true;
        }
        List<TAccountUserDivideDetail> itemDivide = new ArrayList<>();
        //计算普通分成人员的开票收入 todo
        for (TConfigUserCommission userCommission : userCommissionsDeputy) {
            //查询医院详情
            TConfigHospital configHospital = configHospitalDao.selectInfoById(userCommission.getHospitalId());
            TConfigItem configItem = configItemDao.selectInfoById(userCommission.getItemId());
            if(Objects.isNull(commissionMain) || Objects.isNull(configItem)){
                continue;
            }
            //计算普通分成人员-项目开票总金额、分成金额
            TAccountUserDivideDetail itemDivideDeputy = new TAccountUserDivideDetail();
            itemDivideDeputy.setBelongId(userCommission.getId());
            itemDivideDeputy.setBelongName(userCommission.getName());
            itemDivideDeputy.setHospitalId(userCommission.getHospitalId());
            itemDivideDeputy.setHospitalName(configHospital.getHospitalName());
            itemDivideDeputy.setItemId(userCommission.getId());
            itemDivideDeputy.setItemName(configItem.getItemName());
            itemDivideDeputy.setCommission(userCommission.getCommission());
            itemDivideDeputy.setStatus((byte) UserStatusEnum.ENABLED.getCode());
            itemDivideDeputy.setCommissionType(DivideCommissionTypeEnum.MAIN.getType().byteValue());
            //根据医院、项目查找票据
            BigDecimal invoiceMoney = accountRecordDao.calculateInvoiceAmount(settleDate, userCommission.getId(), userCommission.getId(), userCommission.getId());
            if (Objects.isNull(invoiceMoney)) {
                invoiceMoney = new BigDecimal(0);
            }
            itemDivideDeputy.setInvoiceTotalMoney(invoiceMoney);
            BigDecimal income = invoiceMoney.multiply(userCommission.getCommission()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            itemDivideDeputy.setIncome(income);
            itemDivide.add(itemDivideDeputy);

            //计算主分成人员的项目提成
            TAccountUserDivideDetail itemDivideMain = new TAccountUserDivideDetail();
            BeanUtils.copyProperties(itemDivideDeputy, itemDivideMain);
            itemDivideMain.setCommissionType(DivideCommissionTypeEnum.DEPUTY.getType().byteValue());
            itemDivideMain.setBelongId(commissionMain.getId());
            itemDivideMain.setBelongName(commissionMain.getName());
            itemDivideMain.setDivideId(mainDivideId);
            BigDecimal mainCommission = configItem.getCommission().subtract(userCommission.getCommission());
            itemDivideMain.setCommission(mainCommission);

            BigDecimal mainIncome = invoiceMoney.multiply(itemDivideMain.getCommission()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            itemDivideMain.setIncome(mainIncome);
            itemDivide.add(itemDivideMain);
        }

        //todo 保存
        logger.info("[generateStatistics] end settleDate:{}", settleDate);
        return true;
    }

    /**
     * 生成主提成人员数据
     *
     * @param commissionMain 提成人员
     * @param settleDate     月份
     */
    private Long generateMainUserDivide(TConfigUserCommission commissionMain, String settleDate) {
        logger.info("generateMainUserDivide start, commissionMain:{}， settleDate：{}", commissionMain, settleDate);
        TAccountUserDivide userDivide = new TAccountUserDivide();
        userDivide.setMonth(settleDate);
        userDivide.setBelongId(commissionMain.getId());
        userDivide.setBelongName(commissionMain.getName());
        userDivide.setStatus((byte) UserStatusEnum.ENABLED.getCode());
        userDivide.setInvoiceTotalMoney(new BigDecimal(0));
        userDivide.setIncome(new BigDecimal(0));
        userDivide.setCommissionMoney(new BigDecimal(0));
        //保存人员总收益
        int insert = accountUserDivideDao.insert(userDivide);
        if (insert <= 0) {
            logger.info("generateMainUserDivide save fail, userDivide:{}", userDivide);
            return 0L;
        }
        Long divideId = userDivide.getId();
        //计算每个医院-项目的开票收入
        boolean mainItemDivide = generateMainItemDivide(divideId, settleDate, commissionMain);
        if (mainItemDivide) {
            logger.info("generateMainUserDivide item success, commissionMain:{}， settleDate：{}", commissionMain, settleDate);
            //计算总收入
            TAccountUserDivideDetail divideDetail = accountUserDivideDetailDao.calculateItemInvoiceAmount(settleDate, divideId, DivideCommissionTypeEnum.MAIN.getType());
            //更新总收入
            TAccountUserDivide updateDivide = new TAccountUserDivide();
            userDivide.setId(divideId);
            userDivide.setInvoiceTotalMoney(divideDetail.getInvoiceTotalMoney());
            userDivide.setIncome(divideDetail.getIncome());
            accountUserDivideDao.update(updateDivide);
        }
        return divideId;
    }

    /**
     * 生成医院账单
     *
     * @param settleDate
     * @param commissionMain
     * @return
     */
    private boolean generateMainItemDivide(Long did, String settleDate, TConfigUserCommission commissionMain) {
        try {
            //获取医院数据
            int pageNo = 1;
            int pageSize = 50;
            int pageIndex = (pageNo - 1) * pageSize;
            List<TConfigHospital> configHospitals = configHospitalDao.selectHospitalConfigList(null, pageIndex, pageSize);
            if (CollectionUtils.isEmpty(configHospitals)) {
                return false;
            }
            List<TAccountUserDivideDetail> itemDivideList = new ArrayList<>();
            //根据医院获取项目配置
            for (TConfigHospital hospital : configHospitals) {
                List<TAccountUserDivideDetail> itemDivides = calculateMainHospitalItem(hospital, commissionMain, settleDate);
                if (CollectionUtils.isEmpty(itemDivides)) {
                    continue;
                }
                itemDivideList.addAll(itemDivides);
                logger.info("calculateMainHospitalItem, result:{}", itemDivides);
            }
            //保存详细数据
            itemDivideList.forEach(detail -> detail.setDivideId(did));
            accountUserDivideDetailDao.batchInsert(itemDivideList);
            return true;
        } catch (Exception e) {
            logger.error("calculateHospital exception, did:{},settleDate:{}", did, settleDate, e);
        }
        return false;
    }

    /**
     * 计算医院 项目收入
     *
     * @param hospital
     * @param commissionMain
     * @param settleDate
     * @return
     */
    private List<TAccountUserDivideDetail> calculateMainHospitalItem(TConfigHospital hospital, TConfigUserCommission commissionMain, String settleDate) {
        List<TConfigItem> tConfigItems = configItemDao.selectItemConfigListByHid(hospital.getId());
        if (CollectionUtils.isEmpty(tConfigItems)) {
            return Collections.emptyList();
        }
        List<TAccountUserDivideDetail> itemDivide = new ArrayList<>();
        for (TConfigItem item : tConfigItems) {
            TAccountUserDivideDetail itemDivideDetail = new TAccountUserDivideDetail();
            itemDivideDetail.setBelongId(commissionMain.getId());
            itemDivideDetail.setBelongName(commissionMain.getName());
            itemDivideDetail.setHospitalId(hospital.getId());
            itemDivideDetail.setHospitalName(hospital.getHospitalName());
            itemDivideDetail.setItemId(item.getId());
            itemDivideDetail.setItemName(item.getItemName());
            itemDivideDetail.setCommission(item.getCommission());
            itemDivideDetail.setStatus((byte) UserStatusEnum.ENABLED.getCode());
            itemDivideDetail.setCommissionType(DivideCommissionTypeEnum.MAIN.getType().byteValue());
            //根据医院、项目查找票据
            BigDecimal invoiceMoney = accountRecordDao.calculateInvoiceAmount(settleDate, hospital.getId(), item.getId(), commissionMain.getId());
            if (Objects.isNull(invoiceMoney)) {
                invoiceMoney = new BigDecimal(0);
            }
            itemDivideDetail.setInvoiceTotalMoney(invoiceMoney);
            BigDecimal income = invoiceMoney.multiply(item.getCommission()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            itemDivideDetail.setIncome(income);

            itemDivide.add(itemDivideDetail);
        }
        return itemDivide;
    }

    @Override
    public boolean rebuild(Long did) {
        return false;
    }
}
