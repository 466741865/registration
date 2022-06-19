package com.yidian.registration.service.impl;

import com.yidian.registration.dao.*;
import com.yidian.registration.entity.*;
import com.yidian.registration.enums.StatusEnum;
import com.yidian.registration.enums.UserStatusEnum;
import com.yidian.registration.exception.BizException;
import com.yidian.registration.service.IAccountStatisticsService;
import com.yidian.registration.utils.DateBuilder;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.account.statistics.AccountStatisticsDetailVo;
import com.yidian.registration.vo.account.statistics.AccountStatisticsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @Date: 2022/6/18 17:00
 */
@Service
public class AccountStatisticsServiceImpl implements IAccountStatisticsService {

    Logger logger = LoggerFactory.getLogger(AccountStatisticsServiceImpl.class);

    @Resource
    private TConfigHospitalDao configHospitalDao;

    @Resource
    private TConfigItemDao configItemDao;

    @Resource
    private TAccountRecordDao accountRecordDao;

    @Resource
    private TAccountStatisticsDao accountStatisticsDao;

    @Resource
    private TAccountStatisticsDetailDao accountStatisticsDetailDao;


    @Override
    public PageVo<List<AccountStatisticsVo>> getAccountStatisticsList(String settleDate, Long hospitalId, Integer pageNo, Integer pageSize) {
        logger.info("getAccountStatisticsList start, settleDate:{}, hospitalId:{}, pageNo:{}, pageSize:{}", settleDate, hospitalId, pageNo, pageSize);
        PageVo<List<AccountStatisticsVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(pageNo);
        pageVo.setPageSize(pageSize);
        //查询总量
        int configListTotal = accountStatisticsDao.selectListTotal(hospitalId, settleDate);
        if (configListTotal <= 0) {
            return pageVo;
        }
        pageVo.setCount(configListTotal);

        int index = (pageNo - 1) * pageSize;
        List<TAccountStatistics> recordList = accountStatisticsDao.selectList(hospitalId, settleDate, index, pageSize);
        if (CollectionUtils.isEmpty(recordList)) {
            return pageVo;
        }
        List<AccountStatisticsVo> list = new ArrayList<>();
        for (TAccountStatistics record : recordList) {
            AccountStatisticsVo vo = entityToVo(record);
            list.add(vo);
        }
        pageVo.setData(list);
        logger.info("getAccountStatisticsList end, settleDate:{}, hospitalId:{}, pageNo:{}, pageSize:{}, pageVo:{}", settleDate, hospitalId, pageNo, pageSize, pageVo);
        return pageVo;
    }

    private AccountStatisticsVo entityToVo(TAccountStatistics record) {
        AccountStatisticsVo vo = new AccountStatisticsVo();
        vo.setId(record.getId());
        vo.setMonth(record.getMonth());
        vo.setHospitalId(record.getHospitalId());
        vo.setHospitalName(record.getHospitalName());
        vo.setInvoiceTotalMoney(record.getInvoiceTotalMoney());
        vo.setBasicSalary(record.getBasicSalary());
        vo.setIncome(record.getIncome());
        vo.setStatus(record.getStatus());
        vo.setCreateTime(DateBuilder.formatDate(record.getCreateTime(), DateBuilder.FORMAT_FULL));
        vo.setUpdateTime(DateBuilder.formatDate(record.getUpdateTime(), DateBuilder.FORMAT_FULL));
        return vo;
    }

    @Override
    public AccountStatisticsVo getAccountStatisticsDeail(Long id) {
        TAccountStatistics tAccountStatistics = accountStatisticsDao.selectInfoById(id);
        if (Objects.isNull(tAccountStatistics)) {
            return null;
        }
        AccountStatisticsVo accountStatisticsVo = entityToVo(tAccountStatistics);
        List<AccountStatisticsDetailVo> detailList = new ArrayList<>();
        //查询详情
        List<TAccountStatisticsDetail> statisticsDetails = accountStatisticsDetailDao.selectDetailBySid(id);
        if (CollectionUtils.isEmpty(statisticsDetails)) {
            accountStatisticsVo.setDetailList(detailList);
            accountStatisticsVo.setDetailList(Collections.emptyList());
            return accountStatisticsVo;
        }
        for (TAccountStatisticsDetail detail : statisticsDetails) {
            AccountStatisticsDetailVo detailVo = new AccountStatisticsDetailVo();
            detailVo.setId(detail.getId());
            detailVo.setStatisticsId(detail.getStatisticsId());
            detailVo.setItemId(detail.getItemId());
            detailVo.setItemName(detail.getItemName());
            detailVo.setStatus(detail.getStatus());
            detailVo.setCommission(detail.getCommission());
            detailVo.setTotalMoney(detail.getTotalMoney());
            detailVo.setIncome(detail.getIncome());

            detailList.add(detailVo);
        }
        accountStatisticsVo.setDetailList(detailList);
        return accountStatisticsVo;
    }

    @Override
    public PageVo<List<AccountStatisticsDetailVo>> getItemDetail(Long sid) {
        PageVo<List<AccountStatisticsDetailVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(1);
        pageVo.setPageSize(100);
        AccountStatisticsVo statisticsDeail = getAccountStatisticsDeail(sid);
        if (Objects.isNull(statisticsDeail)) {
            return pageVo;
        }
        List<AccountStatisticsDetailVo> detailList = statisticsDeail.getDetailList();
        pageVo.setData(detailList);
        pageVo.setCount(detailList.size());
        return pageVo;
    }

    @Override
    public boolean generateStatistics(String settleDate, Long hospitalId) {
        logger.info("[generateStatistics] start settleDate:{}, hospitalId:{}", settleDate, hospitalId);
        //检查当月是否存在账单
        List<TAccountStatistics> tAccountStatisticsList = accountStatisticsDao.selectInfoByMonth(settleDate, hospitalId);
        if (!CollectionUtils.isEmpty(tAccountStatisticsList)) {
            throw new BizException(StatusEnum.NODATA_CODE.getCode(), "当月已存在有效账单");
        }

        int pageNo = 1;
        int pageSize = 50;
        //查找配置医院
        int pageIndex = (pageNo - 1) * pageSize;
        List<TConfigHospital> configHospitals = configHospitalDao.selectHospitalList(hospitalId, pageIndex, pageSize);
        if (CollectionUtils.isEmpty(configHospitals)) {
            return true;
        }
        for (TConfigHospital hospital : configHospitals) {

            //计算医院收入
            boolean generateStatistics = generateStatistics(settleDate, hospital);
            logger.info("calculateHospital, result:{}", generateStatistics);

        }
        logger.info("[generateStatistics] end settleDate:{}", settleDate);
        return true;
    }

    /**
     * 生成账单
     *
     * @param settleDate
     * @param hospital
     * @return
     */
    private boolean generateStatistics(String settleDate, TConfigHospital hospital) {
        try {
            //计算医院收入
            TAccountStatistics accountStatistics = calculateHospital(hospital, settleDate);

            //保存统计主数据
            int insert = accountStatisticsDao.insert(accountStatistics);
            if (insert <= 0) {
                logger.info("generateStatistics save fail, hospital:{}", accountStatistics);
                return false;
            }
            Long statisticsId = accountStatistics.getId();
            logger.info("generateStatistics statisticsId:{}", statisticsId);
            List<TAccountStatisticsDetail> itemStatistics = accountStatistics.getItemStatistics();
            //保存详细数据
            itemStatistics.forEach(detail -> detail.setStatisticsId(statisticsId));
            accountStatisticsDetailDao.batchInsert(itemStatistics);
            return true;
        } catch (Exception e) {
            logger.error("calculateHospital exception, hospital:{}", hospital, e);
        }
        return false;
    }

    /**
     * 计算医院收入
     *
     * @param hospital
     * @param settleDate
     */
    private TAccountStatistics calculateHospital(TConfigHospital hospital, String settleDate) {
        //查找医院配置项目
        List<TConfigItem> tConfigItems = configItemDao.selectItemConfigListByHid(hospital.getId());
        if (CollectionUtils.isEmpty(tConfigItems)) {
            return null;
        }
        TAccountStatistics hospitalStatistics = new TAccountStatistics();
        hospitalStatistics.setMonth(settleDate);
        hospitalStatistics.setHospitalId(hospital.getId());
        hospitalStatistics.setHospitalName(hospital.getHospitalName());
        hospitalStatistics.setBasicSalary(hospital.getBasicSalary());
        hospitalStatistics.setStatus((byte) UserStatusEnum.ENABLED.getCode());
        //计算项目收入
        List<TAccountStatisticsDetail> itemStatistics = calculateHospitalitem(hospital.getId(), settleDate);
        hospitalStatistics.setItemStatistics(itemStatistics);

        //计算总收入=开票金额总和
        BigDecimal invoiceTotalMoney = new BigDecimal(0);
        BigDecimal totalIncome = new BigDecimal(0);
        for (TAccountStatisticsDetail detail : itemStatistics) {
            invoiceTotalMoney = invoiceTotalMoney.add(detail.getTotalMoney());
            totalIncome = totalIncome.add(detail.getIncome());
        }
        hospitalStatistics.setInvoiceTotalMoney(invoiceTotalMoney);
        hospitalStatistics.setIncome(totalIncome);
        return hospitalStatistics;
    }

    /**
     * 计算医院 项目收入
     *
     * @param hospitalId
     * @param settleDate
     * @return
     */
    private List<TAccountStatisticsDetail> calculateHospitalitem(Long hospitalId, String settleDate) {
        List<TConfigItem> tConfigItems = configItemDao.selectItemConfigListByHid(hospitalId);
        if (CollectionUtils.isEmpty(tConfigItems)) {
            return Collections.emptyList();
        }
        List<TAccountStatisticsDetail> itemStatistics = new ArrayList<>();
        for (TConfigItem item : tConfigItems) {
            TAccountStatisticsDetail statisticsDetail = new TAccountStatisticsDetail();
            statisticsDetail.setItemId(item.getId());
            statisticsDetail.setItemName(item.getItemName());
            statisticsDetail.setCommission(item.getCommission());
            statisticsDetail.setStatus((byte) UserStatusEnum.ENABLED.getCode());
            //根据医院、项目查找票据
            BigDecimal invoiceMoney = accountRecordDao.calculateInvoiceAmount(settleDate, hospitalId, item.getId());
            if (Objects.isNull(invoiceMoney)) {
                invoiceMoney = new BigDecimal(0);
            }
            statisticsDetail.setTotalMoney(invoiceMoney);
            BigDecimal income = invoiceMoney.multiply(item.getCommission()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            statisticsDetail.setIncome(income);

            itemStatistics.add(statisticsDetail);
        }
        return itemStatistics;
    }


    @Override
    public boolean rebuild(Long id) {
        TAccountStatistics tAccountStatistics = accountStatisticsDao.selectInfoById(id);
        if (Objects.isNull(tAccountStatistics)) {
            return false;
        }
        //删除当前的
        int deleteById = accountStatisticsDao.deleteById(id);
        if (deleteById > 0) {
            accountStatisticsDetailDao.deleteBySid(id);
            //查询医院详情
            TConfigHospital hospital = configHospitalDao.selectInfoById(tAccountStatistics.getHospitalId());
            //重新生成
            boolean generateStatistics = generateStatistics(tAccountStatistics.getMonth(), hospital);
            logger.info("rebuild result:{}", generateStatistics);
            return generateStatistics;
        }
        return false;
    }
}
