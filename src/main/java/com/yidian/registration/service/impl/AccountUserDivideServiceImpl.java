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
import org.springframework.util.StringUtils;

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
    private TConfigUserDao configUserDao;

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
            //查询用户类型
            TConfigUser user = configUserDao.selectInfoById(vo.getBelongId());
            if (Objects.nonNull(user)) {
                vo.setUserType((int) user.getType());
            }
            if (UserCommissionTypeEnum.MAIN.getType().equals(vo.getUserType())) {
                //总收入 = 开票分成 + 普通提成
                BigDecimal totalIncomeMoney = new BigDecimal(0);
                totalIncomeMoney = totalIncomeMoney.add(divide.getIncome()).add(divide.getCommissionMoney());
                vo.setIncome(totalIncomeMoney);
            }
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
        vo.setCommissionTotalMoney(divide.getCommissionTotalMoney());
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
        List<AccountDivideDetailVo> detailList = getDivideItemDetail(did, DivideCommissionTypeEnum.INVOICE.getType());
        accountDivideVo.setItemDivides(detailList);
        //是否是主类型
        TConfigUser user = configUserDao.selectInfoById(accountDivideVo.getBelongId());
        if (Objects.nonNull(user) && UserCommissionTypeEnum.MAIN.getType().equals((int) user.getType())) {
            //查询票据提成收入
            List<AccountDivideDetailVo> commissionDetailList = getDivideItemDetail(did, DivideCommissionTypeEnum.COMMISSION.getType());
            accountDivideVo.setItemDivides(commissionDetailList);
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
        //指定人员计算分成
        if (Objects.nonNull(belongId)) {
            TConfigUser user = configUserDao.selectInfoById(belongId);
            boolean calculateUserDivide = calculateUserDivide(settleDate, user, null);
            logger.info("[generateStatistics] end settleDate:{}, belongId:{}, result:{}", settleDate, belongId, calculateUserDivide);
            return calculateUserDivide;
        }
        //计算所有人员分成
        //查找主分成人员
        List<TConfigUser> users = configUserDao.selectListByType(UserCommissionTypeEnum.MAIN.getType());
        if (CollectionUtils.isEmpty(users)) {
            throw new BizException(StatusEnum.NODATA_CODE.getCode(), "缺少核心人员配置");
        }
        TConfigUser userMain = users.get(0);

        //计算主分成人员的 开票收入
        Long mainDivideId = generateMainUserDivide(userMain, settleDate);
        if (mainDivideId <= 0) {
            logger.info("[generateDivide] calculateUserDivide fail, settleDate:{}, belongId:{}", settleDate, belongId);
            throw new BizException(StatusEnum.NODATA_CODE.getCode(), "计算分成失败");
        }
        //查询普通分成人员
        List<TConfigUser> userDeputys = configUserDao.selectListByType(UserCommissionTypeEnum.DEPUTY.getType());
        if (CollectionUtils.isEmpty(userDeputys)) {
            logger.info("[generateDivide] not query user commission, settleDate:{}, belongId:{}", settleDate, belongId);
            return true;
        }
        //计算普通分成人员-分成数据
        generateDeputyUsersDivide(settleDate, mainDivideId, userMain, userDeputys);
        logger.info("[generateStatistics] end settleDate:{}, belongId:{}", settleDate, belongId);
        return true;
    }

    /**
     * 生成主提成人员数据
     *
     * @param userMain   提成人员
     * @param settleDate 月份
     */
    private Long generateMainUserDivide(TConfigUser userMain, String settleDate) {
        logger.info("generateMainUserDivide start, userMain:{}， settleDate：{}", userMain, settleDate);
        TAccountUserDivide userDivide = new TAccountUserDivide();
        userDivide.setMonth(settleDate);
        userDivide.setBelongId(userMain.getId());
        userDivide.setBelongName(userMain.getName());
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
        boolean mainItemDivide = generateMainItemDivide(divideId, settleDate, userMain);
        if (mainItemDivide) {
            logger.info("generateMainUserDivide item success, userMain:{}， settleDate：{}", userMain, settleDate);
            //计算总收入
            TAccountUserDivideDetail divideDetail = accountUserDivideDetailDao.calculateItemInvoiceAmount(divideId, DivideCommissionTypeEnum.INVOICE.getType());
            //更新总收入
            TAccountUserDivide updateDivide = new TAccountUserDivide();
            updateDivide.setId(divideId);
            updateDivide.setInvoiceTotalMoney(divideDetail.getInvoiceTotalMoney());
            updateDivide.setIncome(divideDetail.getIncome());
            accountUserDivideDao.update(updateDivide);
        }
        return divideId;
    }

    /**
     * 计算普通用户们的分成数据
     *
     * @param settleDate
     * @param mainDivideId
     * @param userMain
     * @param userDeputys
     * @return
     */
    private boolean generateDeputyUsersDivide(String settleDate, Long mainDivideId, TConfigUser userMain, List<TConfigUser> userDeputys) {

        if (StringUtils.isEmpty(settleDate)) {
            return false;
        }
        boolean result = false;
        for (TConfigUser user : userDeputys) {
            //计算普通人员的分成信息
            boolean deputyUserDivide = generateDeputyUserDivide(settleDate, user, mainDivideId);
            if (deputyUserDivide) {
                logger.info("[generateDeputyUsersDivide] deputy user divide success, settleDate：{}, user:{}， ", settleDate, user);
                result = deputyUserDivide;
            }
        }
        //计算主用户的抽成总收入
        if (result) {
            logger.info("[generateDeputyUsersDivide] deputy user divide success, userMain:{}， settleDate：{}", userMain, settleDate);
            generateMainUsersCommission(mainDivideId);
        }
        return true;
    }

    /**
     * 计算主用户的抽成
     *
     * @param mainDivideId
     * @return
     */
    private boolean generateMainUsersCommission(Long mainDivideId) {
        logger.info("[generateMainUsersCommission] main user commission, mainDivideId:{}", mainDivideId);
        //计算总抽成的开票金额、抽成数据
        TAccountUserDivideDetail invoiceAmount = accountUserDivideDetailDao.calculateItemInvoiceAmount(mainDivideId, DivideCommissionTypeEnum.COMMISSION.getType());
        if (Objects.isNull(invoiceAmount)) {
            return true;
        }
        //查询主分成信息
        TAccountUserDivide mainDivide = accountUserDivideDao.selectInfoById(mainDivideId);
        if (Objects.isNull(mainDivide)) {
            return true;
        }
        //更新总抽成
        TAccountUserDivide updateInvoiceAmount = new TAccountUserDivide();
        updateInvoiceAmount.setId(mainDivideId);
        updateInvoiceAmount.setCommissionTotalMoney(invoiceAmount.getInvoiceTotalMoney());
        updateInvoiceAmount.setCommissionMoney(invoiceAmount.getIncome());
        accountUserDivideDao.update(updateInvoiceAmount);
        return true;
    }

    /**
     * 生成副提成人员数据
     *
     * @param userDeputy 提成人员
     * @param settleDate 月份
     */
    private boolean generateDeputyUserDivide(String settleDate, TConfigUser userDeputy, Long mainDivideId) {
        logger.info("generateDeputyUserDivide start, userDeputy:{}， settleDate：{}", userDeputy, settleDate);
        try {
            TAccountUserDivide userDivide = new TAccountUserDivide();
            userDivide.setMonth(settleDate);
            userDivide.setBelongId(userDeputy.getId());
            userDivide.setBelongName(userDeputy.getName());
            userDivide.setStatus((byte) UserStatusEnum.ENABLED.getCode());
            userDivide.setInvoiceTotalMoney(new BigDecimal(0));
            userDivide.setIncome(new BigDecimal(0));
            userDivide.setCommissionMoney(new BigDecimal(0));
            userDivide.setCommissionTotalMoney(new BigDecimal(0));
            //保存普通人员总收益
            int insert = accountUserDivideDao.insert(userDivide);
            if (insert <= 0) {
                logger.info("generateDeputyUserDivide save fail, userDivide:{}", userDivide);
                return false;
            }
            //获取个人员的项目提成配置
            List<TConfigUserCommission> userCommissionsDeputy = configUserCommissionDao.selectUserCommissionConfigList(userDeputy.getId());
            if (CollectionUtils.isEmpty(userCommissionsDeputy)) {
                logger.info("[generateDeputyUserDivide] not query user commission, settleDate:{}, belongId:{}", settleDate, userDeputy.getId());
                return false;
            }

            Long divideId = userDivide.getId();

            //计算普通分成人员项目分成明细
            boolean deputyItemDivide = generateDeputyItemDivide(settleDate, userDeputy, mainDivideId, divideId, userCommissionsDeputy);
            if (deputyItemDivide) {
                //计算普通用户的分成总收入
                logger.info("generateDeputyUserDivide item success, mainDivideId:{}， settleDate：{}", mainDivideId, settleDate);
                //查询
                TAccountUserDivide divide = accountUserDivideDao.selectInfoById(divideId);
                //计算总收入
                TAccountUserDivideDetail divideDetail = accountUserDivideDetailDao.calculateItemInvoiceAmount(divideId, DivideCommissionTypeEnum.INVOICE.getType());
                //更新总收入
                TAccountUserDivide updateDivide = new TAccountUserDivide();
                updateDivide.setId(divideId);
                if (Objects.nonNull(divideDetail) && Objects.nonNull(divideDetail.getInvoiceTotalMoney())) {
                    updateDivide.setInvoiceTotalMoney(divide.getInvoiceTotalMoney().add(divideDetail.getInvoiceTotalMoney()));
                }
                if (Objects.nonNull(divideDetail) && Objects.nonNull(divideDetail.getIncome())) {
                    updateDivide.setIncome(divide.getIncome().add(divideDetail.getIncome()));
                }
                accountUserDivideDao.update(updateDivide);
            }
            logger.info("generateDeputyUserDivide end, userDeputy:{}， settleDate：{}, userDeputy:{}", userDeputy, settleDate, userDeputy);
            return true;
        } catch (Exception e) {
            logger.error("generateDeputyUserDivide exception,userDeputy:{}， settleDate：{}", userDeputy, settleDate, e);
        }
        return false;
    }

    /**
     * 计算普通分成人员项目分成明细
     *
     * @param settleDate
     * @param userDeputy
     * @param mainDivideId
     * @param userCommissionsDeputy
     * @return
     */
    private boolean generateDeputyItemDivide(String settleDate, TConfigUser userDeputy, Long mainDivideId, Long divideId, List<TConfigUserCommission> userCommissionsDeputy) {
        //普通用户项目分成明细
        List<TAccountUserDivideDetail> deputyItemDivides = new ArrayList<>();
        //主用户项目提成明细
        List<TAccountUserDivideDetail> mainItemDivides = new ArrayList<>();
        //计算每个项目的总开票金额
        for (TConfigUserCommission userCommission : userCommissionsDeputy) {
            //查询医院详情
            TConfigHospital configHospital = configHospitalDao.selectInfoById(userCommission.getHospitalId());
            TConfigItem configItem = configItemDao.selectInfoById(userCommission.getItemId());
            if (Objects.isNull(configHospital) || Objects.isNull(configItem)) {
                continue;
            }
            //计算普通分成人员-项目开票总金额、分成金额
            TAccountUserDivideDetail itemDivideDeputy = new TAccountUserDivideDetail();
            itemDivideDeputy.setDivideId(divideId);
            itemDivideDeputy.setBelongId(userDeputy.getId());
            itemDivideDeputy.setBelongName(userDeputy.getName());
            itemDivideDeputy.setHospitalId(userCommission.getHospitalId());
            itemDivideDeputy.setHospitalName(configHospital.getHospitalName());
            itemDivideDeputy.setItemId(userCommission.getId());
            itemDivideDeputy.setItemName(configItem.getItemName());
            itemDivideDeputy.setCommission(userCommission.getCommission());
            itemDivideDeputy.setStatus((byte) UserStatusEnum.ENABLED.getCode());
            itemDivideDeputy.setCommissionType(DivideCommissionTypeEnum.INVOICE.getType().byteValue());
            //根据医院、项目获取开票总收入
            BigDecimal itemInvoiceMoney = accountRecordDao.calculateInvoiceAmount(settleDate, userCommission.getHospitalId(), userCommission.getItemId(), userCommission.getBelongId());
            if (Objects.isNull(itemInvoiceMoney)) {
                itemInvoiceMoney = new BigDecimal(0);
            }
            itemDivideDeputy.setInvoiceTotalMoney(itemInvoiceMoney);
            BigDecimal income = itemInvoiceMoney.multiply(userCommission.getCommission()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            itemDivideDeputy.setIncome(income);
            deputyItemDivides.add(itemDivideDeputy);

            //计算主分成人员的项目提成
            TAccountUserDivideDetail itemDivideMain = new TAccountUserDivideDetail();
            BeanUtils.copyProperties(itemDivideDeputy, itemDivideMain);
            itemDivideMain.setDivideId(mainDivideId);
            itemDivideMain.setCommissionType(DivideCommissionTypeEnum.COMMISSION.getType().byteValue());
            BigDecimal mainCommission = configItem.getCommission().subtract(userCommission.getCommission());
            itemDivideMain.setCommission(mainCommission);
            itemDivideMain.setIncome(new BigDecimal(0));
            BigDecimal mainIncome = itemInvoiceMoney.multiply(itemDivideMain.getCommission()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            itemDivideMain.setIncome(mainIncome);
            mainItemDivides.add(itemDivideMain);
        }
        //保存 普通用户的分成
        accountUserDivideDetailDao.batchInsert(deputyItemDivides);
        //保存主用户的抽成
        accountUserDivideDetailDao.batchInsert(mainItemDivides);
        return true;
    }

    /**
     * 生成医院账单
     *
     * @param settleDate
     * @param userMain
     * @return
     */
    private boolean generateMainItemDivide(Long did, String settleDate, TConfigUser userMain) {
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
                List<TAccountUserDivideDetail> itemDivides = calculateMainHospitalItem(hospital, userMain, settleDate);
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
     * @param userMain
     * @param settleDate
     * @return
     */
    private List<TAccountUserDivideDetail> calculateMainHospitalItem(TConfigHospital hospital, TConfigUser userMain, String settleDate) {
        List<TConfigItem> tConfigItems = configItemDao.selectItemConfigListByHid(hospital.getId());
        if (CollectionUtils.isEmpty(tConfigItems)) {
            return Collections.emptyList();
        }
        List<TAccountUserDivideDetail> itemDivide = new ArrayList<>();
        for (TConfigItem item : tConfigItems) {
            TAccountUserDivideDetail itemDivideDetail = new TAccountUserDivideDetail();
            itemDivideDetail.setBelongId(userMain.getId());
            itemDivideDetail.setBelongName(userMain.getName());
            itemDivideDetail.setHospitalId(hospital.getId());
            itemDivideDetail.setHospitalName(hospital.getHospitalName());
            itemDivideDetail.setItemId(item.getId());
            itemDivideDetail.setItemName(item.getItemName());
            itemDivideDetail.setCommission(item.getCommission());
            itemDivideDetail.setStatus((byte) UserStatusEnum.ENABLED.getCode());
            itemDivideDetail.setCommissionType(DivideCommissionTypeEnum.INVOICE.getType().byteValue());
            //根据医院、项目查找票据
            BigDecimal invoiceMoney = accountRecordDao.calculateInvoiceAmount(settleDate, hospital.getId(), item.getId(), userMain.getId());
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
        logger.info("rebuild start did:{}", did);
        //查询分成主信息
        TAccountUserDivide userDivide = accountUserDivideDao.selectInfoById(did);
        if (Objects.isNull(userDivide)) {
            logger.info("rebuild not query divide info did:{}", did);
            return true;
        }
        //查询用户明细
        TConfigUser user = configUserDao.selectInfoById(userDivide.getBelongId());
        //清除旧分成记录
        boolean deleteResult = deleteUserDivideInfo(did, user);
        if (!deleteResult) {
            logger.info("rebuild delete old divide info fail, did:{}", did);
            return false;
        }
        //重新生成分成记录
        boolean calculateUserDivide = calculateUserDivide(userDivide.getMonth(), user, did);
        logger.info("rebuild deputy userDivide end, did:{}, result：{}", did, calculateUserDivide);
        return calculateUserDivide;
    }

    /**
     * 计算用户开票分成
     *
     * @param month
     * @param user
     * @return
     */
    private boolean calculateUserDivide(String month, TConfigUser user, Long oldDid) {
        if (StringUtils.isEmpty(month)) {
            return false;
        }
        if (UserCommissionTypeEnum.MAIN.getType().equals((int) user.getType())) {
            logger.info("calculateUserDivide main userDivide oldDid:{}，month：{}", oldDid, month);
            //计算主分成人员的 开票收入
            Long mainDivideId = generateMainUserDivide(user, month);
            if (mainDivideId > 0 && Objects.nonNull(oldDid)) {
                logger.info("calculateUserDivide main userDivide oldDid:{}，mainDivideId：{}", oldDid, mainDivideId);
                //将有效的抽成记录归为新的主分成
                accountUserDivideDetailDao.updateDidByDid(oldDid, mainDivideId, DivideCommissionTypeEnum.COMMISSION.getType());
                generateMainUsersCommission(mainDivideId);
                return true;
            }
            return false;
        } else {
            logger.info("calculateUserDivide deputy userDivide oldDid:{}，month：{}", oldDid, month);
            //查询主用户信息
            List<TConfigUser> users = configUserDao.selectListByType(UserCommissionTypeEnum.MAIN.getType());
            if (CollectionUtils.isEmpty(users)) {
                return false;
            }
            TConfigUser mainUser = users.get(0);

            //查询主用户分成信息
            List<TAccountUserDivide> mainUserDivides = accountUserDivideDao.selectInfoByMonth(mainUser.getId(), month);
            if (CollectionUtils.isEmpty(mainUserDivides)) {
                return false;
            }
            TAccountUserDivide mainUserDivide = mainUserDivides.get(0);
            //计算普通分成人员-分成数据
            boolean result = generateDeputyUsersDivide(month, mainUserDivide.getId(), mainUser, Collections.singletonList(user));
            logger.info("calculateUserDivide deputy userDivide end, month:{} result：{}", month, result);
            return result;
        }
    }

    /**
     * 清除记录
     *
     * @param did
     * @return
     */
    private boolean deleteUserDivideInfo(Long did, TConfigUser user) {
        try {
            TAccountUserDivide userDivide = new TAccountUserDivide();
            userDivide.setId(did);
            userDivide.setStatus((byte) UserStatusEnum.DISABLE.getCode());
            int update = accountUserDivideDao.update(userDivide);
            if (update > 0) {
                //清除开票提成
                accountUserDivideDetailDao.updateStatusByDid(did, user.getId(), UserStatusEnum.DISABLE.getCode(), DivideCommissionTypeEnum.INVOICE.getType());
            }
            //普通用户
            if (UserCommissionTypeEnum.DEPUTY.getType().equals(user.getType().intValue())) {
                //清除抽成提成
                accountUserDivideDetailDao.updateStatusByDid(null, user.getId(), UserStatusEnum.DISABLE.getCode(), DivideCommissionTypeEnum.COMMISSION.getType());
            }
            return true;
        } catch (Exception e) {
            logger.error("deleteUserDivideInfo exception, did:{}", did, e);
        }
        return false;
    }
}
