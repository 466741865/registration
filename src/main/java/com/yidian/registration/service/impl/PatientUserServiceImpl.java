package com.yidian.registration.service.impl;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.dao.TPatientPaymentRecordsDao;
import com.yidian.registration.dao.TPatientUserDao;
import com.yidian.registration.entity.TPatientPaymentRecordsEntity;
import com.yidian.registration.entity.TPatientUserEntity;
import com.yidian.registration.enums.PatientUserStatusEnum;
import com.yidian.registration.enums.SexEnum;
import com.yidian.registration.service.IPatientUserService;
import com.yidian.registration.utils.DateBuilder;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.patientuser.PatientUserAddVo;
import com.yidian.registration.vo.patientuser.PatientUserInfoVo;
import com.yidian.registration.vo.patientuser.PatientUserPayMentInfoVo;
import com.yidian.registration.vo.patientuser.PatientUserPayMentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/1/21 23:29
 */
@Service
public class PatientUserServiceImpl implements IPatientUserService {
    Logger logger = LoggerFactory.getLogger(PatientUserServiceImpl.class);

    @Autowired
    private TPatientUserDao patientUserDao;

    @Autowired
    private TPatientPaymentRecordsDao paymentRecordsDao;

    @Override
    public PageVo<List<PatientUserInfoVo>> getPatientUserList(String patientName, String mobile, Integer pageNo, Integer pageSize) {
        logger.info("getPatientUserList start, patientName:{}, mobile:{}, pageNo:{}, pageSize:{}", patientName, mobile, pageNo, pageSize);
        PageVo<List<PatientUserInfoVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(pageNo);
        pageVo.setPageSize(pageSize);
        //查询总量
        int configListTotal = patientUserDao.getPatientUserListTotal(patientName, mobile);
        if (configListTotal <= 0) {
            return pageVo;
        }
        pageVo.setCount(configListTotal);

        int index = (pageNo - 1) * pageSize;
        List<TPatientUserEntity> configList = patientUserDao.getPatientUserList(patientName, mobile, index, pageSize);
        if (CollectionUtils.isEmpty(configList)) {
            return pageVo;
        }
        List<PatientUserInfoVo> list = new ArrayList<>();
        for (TPatientUserEntity config : configList) {
            PatientUserInfoVo vo = entityToUserInfoVo(config);
            list.add(vo);
        }
        pageVo.setData(list);
        logger.info("getPatientUserList end, patientName:{}, mobile:{}, pageNo:{}, pageSize:{}, pageVo:{}", patientName, mobile, pageNo, pageSize, pageVo);
        return pageVo;
    }

    @Override
    public Boolean addPatientUser(PatientUserAddVo addVo) {
        logger.info("addPatientUser start,configVo:{}", JSON.toJSON(addVo));
        TPatientUserEntity user = new TPatientUserEntity();

        user.setName(addVo.getPatientName());
        user.setSex((byte) addVo.getSex());
        user.setArea(addVo.getArea());
        user.setMobile(addVo.getMobile());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setStatus((byte) PatientUserStatusEnum.NORMAL.getCode());
        user.setRemainNum(0);

        int res = patientUserDao.insert(user);
        if (res > 0) {
            logger.info("addPatientUser success ,id:{}", user.getId());
            return true;
        }
        return false;
    }

    @Override
    public PatientUserInfoVo getInfoByUid(Long uid) {
        logger.info("getInfoByUid start,uid:{}", uid);
        TPatientUserEntity userEntity = patientUserDao.selectInfoById(uid);
        if (Objects.isNull(userEntity)) {
            return null;
        }
        PatientUserInfoVo vo = entityToUserInfoVo(userEntity);
        logger.info("getInfoByUid end, uid:{},res:{}", uid, vo);
        return vo;
    }

    @Override
    public Boolean payMent(PatientUserPayMentVo payMentVo) {
        logger.info("payMent start payMentVo:{}", payMentVo);
        try {
            //查询用户信息
            TPatientUserEntity patientUserEntity = patientUserDao.selectInfoById(payMentVo.getPid());
            if (Objects.isNull(patientUserEntity)) {
                return false;
            }
            //更新治疗次数
            int buyNum = payMentVo.getBuyNum();
            int res = patientUserDao.updateRemainNum(payMentVo.getPid(), buyNum);
            if(res <= 0){
                logger.info("payMent update patient num fail, payMentVo:{}", payMentVo);
                return false;
            }
            patientUserEntity = patientUserDao.selectInfoById(payMentVo.getPid());

            TPatientPaymentRecordsEntity recordsEntity = new TPatientPaymentRecordsEntity();
            recordsEntity.setPid(payMentVo.getPid());
            recordsEntity.setBuyNum(payMentVo.getBuyNum());
            recordsEntity.setPayMoney(new BigDecimal(payMentVo.getPayMoney()));
            recordsEntity.setPayDate(DateBuilder.toDate(payMentVo.getPayDate(), DateBuilder.YYYY_MM_DD));
            recordsEntity.setRemainNum(patientUserEntity.getRemainNum());
            recordsEntity.setCreateTime(new Date());
            recordsEntity.setUpdateTime(new Date());
            int result = paymentRecordsDao.insert(recordsEntity);
            if (result > 0) {
                return true;
            }
        } catch (ParseException e) {
            logger.error("payMent exception,payMentVo:{} ", payMentVo, e);
        }
        logger.info("payMent end payMentVo:{}", payMentVo);
        return false;
    }

    @Override
    public PageVo<List<PatientUserPayMentInfoVo>> getPatientPayMentList(Long pid, Integer pageNo, Integer pageSize) {
        logger.info("getPatientPayMentList start, pid:{}, pageNo:{}, pageSize:{}", pid, pageNo, pageSize);
        PageVo<List<PatientUserPayMentInfoVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(pageNo);
        pageVo.setPageSize(pageSize);
        //查询总量
        int configListTotal = paymentRecordsDao.getPatientPayMentListTotal(pid);
        if (configListTotal <= 0) {
            return pageVo;
        }
        pageVo.setCount(configListTotal);

        int index = (pageNo - 1) * pageSize;
        List<TPatientPaymentRecordsEntity> configList = paymentRecordsDao.getPatientPayMentList(pid, index, pageSize);
        if (CollectionUtils.isEmpty(configList)) {
            return pageVo;
        }
        List<PatientUserPayMentInfoVo> list = new ArrayList<>();
        for (TPatientPaymentRecordsEntity config : configList) {
            PatientUserPayMentInfoVo vo = entityToPayMentVo(config);
            list.add(vo);
        }
        pageVo.setData(list);
        logger.info("getPatientPayMentList end, patienpid:{}, pageNo:{}, pageSize:{}, pageVo:{}", pid, pageNo, pageSize, pageVo);
        return pageVo;
    }

    /**
     * entity to vo
     *
     * @param userEntity
     * @return
     */
    private PatientUserInfoVo entityToUserInfoVo(TPatientUserEntity userEntity) {
        PatientUserInfoVo vo = new PatientUserInfoVo();
        vo.setId(userEntity.getId());
        vo.setPatientName(userEntity.getName());
        vo.setMobile(userEntity.getMobile());
        vo.setArea(userEntity.getArea());
        vo.setRemainNum(userEntity.getRemainNum());
        vo.setStatus(userEntity.getStatus());
        vo.setCreator("");
        SexEnum sexEnum = SexEnum.getEnum(userEntity.getSex());
        vo.setSex(Objects.isNull(sexEnum) ? "未知" : sexEnum.getDesc());
        return vo;
    }

    /**
     * entity to vo
     *
     * @param recordsEntity
     * @return
     */
    private PatientUserPayMentInfoVo entityToPayMentVo(TPatientPaymentRecordsEntity recordsEntity) {
        PatientUserPayMentInfoVo vo = new PatientUserPayMentInfoVo();
        vo.setPid(recordsEntity.getPid());
        vo.setBuyNum(recordsEntity.getBuyNum());
        vo.setPayDate(DateBuilder.formatDate(recordsEntity.getPayDate(), DateBuilder.FORMAT_FULL));
        vo.setPayMoney(String.valueOf(recordsEntity.getPayMoney()));
        vo.setRemainNum(recordsEntity.getRemainNum());
        return vo;
    }
}
