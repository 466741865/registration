package com.yidian.registration.service.impl;

import com.yidian.registration.dao.*;
import com.yidian.registration.entity.*;
import com.yidian.registration.enums.UserStatusEnum;
import com.yidian.registration.service.IAccountRecordService;
import com.yidian.registration.utils.DateBuilder;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.account.record.*;
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
 * @Date: 2022/6/11 10:34
 */
@Service
public class AccountRecordServiceImpl implements IAccountRecordService {

    Logger logger = LoggerFactory.getLogger(AccountRecordServiceImpl.class);

    @Resource
    private TAccountRecordDao AccountRecordDao;

    @Resource
    private TConfigHospitalDao configHospitalDao;

    @Resource
    private TConfigItemDao configItemDao;

    @Resource
    private TConfigUserDao configUserDao;


    @Override
    public PageVo<List<AccountRecordDetailVo>> getAccountRecordList(String name, String settleDate, String invoiceDate, Long hospitalId, Long itemId, Long belongId, Integer pageNo, Integer pageSize) {
        logger.info("getAccountRecordList start, name:{}, settleDate:{}, hospitalId:{}, itemId:{}, belongId:{}, pageNo:{}, pageSize:{}", name, settleDate, hospitalId, itemId, belongId, pageNo, pageSize);
        PageVo<List<AccountRecordDetailVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(pageNo);
        pageVo.setPageSize(pageSize);
        //查询总量
        int configListTotal = AccountRecordDao.selectRecordListTotal(name, settleDate, invoiceDate, hospitalId, itemId, belongId);
        if (configListTotal <= 0) {
            return pageVo;
        }
        pageVo.setCount(configListTotal);

        int index = (pageNo - 1) * pageSize;
        List<TAccountRecord> recordList = AccountRecordDao.selectRecordList(name, settleDate, invoiceDate, hospitalId, itemId, belongId, index, pageSize);
        if (CollectionUtils.isEmpty(recordList)) {
            return pageVo;
        }
        List<AccountRecordDetailVo> list = new ArrayList<>();
        for (TAccountRecord record : recordList) {
            AccountRecordDetailVo vo = setAccountRecordDetailVo(record);
            list.add(vo);
        }
        pageVo.setData(list);
        logger.info("getAccountRecordList start, name:{}, hospitalId:{}, itemId:{}, belongId:{}, pageNo:{}, pageSize:{}, pageVo:{}", name, pageNo, hospitalId, itemId, belongId, pageSize, pageVo);
        return pageVo;
    }

    /**
     * 实体转换
     *
     * @param record
     * @return
     */
    private AccountRecordDetailVo entityToVo(TAccountRecord record) {
        AccountRecordDetailVo vo = new AccountRecordDetailVo();
        vo.setId(record.getId());
        vo.setHospitalId(record.getHospitalId());
        vo.setBelongId(record.getBelongId());
        vo.setItemId(record.getItemId());
        vo.setInvoiceMoney(record.getInvoiceMoney().toString());
        vo.setPatientName(record.getPatientName());
        vo.setSettleDate(record.getSettleDate());
        vo.setInvoiceDate(record.getInvoiceDate());
        vo.setStatus(record.getStatus());
        vo.setCreateTime(DateBuilder.formatDate(record.getCreateTime(), DateBuilder.FORMAT_FULL));
        vo.setUpdateTime(DateBuilder.formatDate(record.getUpdateTime(), DateBuilder.FORMAT_FULL));
        return vo;
    }


    @Override
    public AccountRecordDetailVo getAccountRecordDeail(Long id) {
        logger.info("getAccountRecordDeail start id:{}", id);
        TAccountRecord record = AccountRecordDao.selectInfoById(id);
        if (Objects.isNull(record)) {
            return null;
        }
        AccountRecordDetailVo vo = setAccountRecordDetailVo(record);
        logger.info("getAccountRecordDeail end id:{}, infoVo:{}", id, vo);
        return vo;
    }

    /**
     * 封装详情
     *
     * @return
     */
    private AccountRecordDetailVo setAccountRecordDetailVo(TAccountRecord record) {
        AccountRecordDetailVo vo = entityToVo(record);
        //查询单位信息
        TConfigHospital hospital = configHospitalDao.selectInfoById(record.getHospitalId());
        if (Objects.nonNull(hospital)) {
            vo.setHospitalName(hospital.getHospitalName());
        }
        //查询项目信息
        TConfigItem item = configItemDao.selectInfoById(record.getItemId());
        if (Objects.nonNull(item)) {
            vo.setItemName(item.getItemName());
        }
        //归属人信息
        TConfigUser user = configUserDao.selectInfoById(record.getBelongId());
        if (Objects.nonNull(user)) {
            vo.setBelongName(user.getName());
        }
        return vo;
    }

    @Override
    public boolean addAccountRecord(AccountRecordAddVo addVo) {
        logger.info("addAccountRecord start addVo:{}", addVo);
        TAccountRecord record = new TAccountRecord();
        record.setPatientName(addVo.getPatientName());
        record.setHospitalId(addVo.getHospitalId());
        record.setItemId(addVo.getItemId());
        record.setBelongId(addVo.getBelongId());
        record.setInvoiceMoney(new BigDecimal(addVo.getInvoiceMoney()));
        record.setInvoiceDate(addVo.getInvoiceDate());
        record.setSettleDate(addVo.getSettleDate());
        record.setStatus((byte) UserStatusEnum.ENABLED.getCode());
        int insert = AccountRecordDao.insert(record);
        logger.info("addAccountRecord end addVo:{}, insertres:{}", addVo, insert);
        return insert > 0;
    }

    @Override
    public boolean batchAddRecord(AccountRecordBatchAddVo addVo) {
        logger.info("addAccountRecord start addVo:{}", addVo);
        List<AccountRecordInfoVo> recordInfoList = addVo.getRecordInfoList();
        List<TAccountRecord> recordList = new ArrayList<>(recordInfoList.size());
        for (AccountRecordInfoVo infoVo : recordInfoList) {
            TAccountRecord record = new TAccountRecord();
            record.setHospitalId(addVo.getHospitalId());
            record.setItemId(addVo.getItemId());
            record.setBelongId(addVo.getBelongId());
            record.setSettleDate(addVo.getSettleDate());
            record.setStatus((byte) UserStatusEnum.ENABLED.getCode());

            record.setPatientName(infoVo.getPatientName());
            record.setInvoiceMoney(new BigDecimal(infoVo.getInvoiceMoney()));
            record.setInvoiceDate(infoVo.getInvoiceDate());
            recordList.add(record);
        }
        int insert = AccountRecordDao.batchInsert(recordList);
        logger.info("addAccountRecord end addVo:{}, insertres:{}", addVo, insert);
        return insert > 0;
    }

    @Override
    public boolean updateAccountRecord(AccountRecordUpdateVo updateVo) {
        TAccountRecord record = AccountRecordDao.selectInfoById(updateVo.getId());
        if (Objects.isNull(record)) {
            return false;
        }
        record.setPatientName(updateVo.getPatientName());
        record.setHospitalId(updateVo.getHospitalId());
        record.setItemId(updateVo.getItemId());
        record.setBelongId(updateVo.getBelongId());
        record.setInvoiceMoney(new BigDecimal(updateVo.getInvoiceMoney()));
        record.setInvoiceDate(updateVo.getInvoiceDate());
        record.setSettleDate(updateVo.getSettleDate());
        record.setStatus(updateVo.getStatus());
        int result = AccountRecordDao.updateRecord(record);
        return result > 0;
    }

    @Override
    public boolean deleteConfig(Long id) {
        TAccountRecord item = new TAccountRecord();
        item.setId(id);
        item.setStatus((byte) UserStatusEnum.DISABLE.getCode());
        int result = AccountRecordDao.updateRecord(item);
        return result > 0;
    }
}
