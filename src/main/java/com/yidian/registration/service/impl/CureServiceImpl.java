package com.yidian.registration.service.impl;

import com.yidian.registration.dao.TPatientCureDao;
import com.yidian.registration.dao.TPatientUserDao;
import com.yidian.registration.entity.TPatientCureEntity;
import com.yidian.registration.entity.TPatientUserEntity;
import com.yidian.registration.enums.PatientCureStateEnum;
import com.yidian.registration.enums.RegistrationOperationEnum;
import com.yidian.registration.enums.StatusEnum;
import com.yidian.registration.service.ICureService;
import com.yidian.registration.utils.DateBuilder;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.cure.PatientCureAddVo;
import com.yidian.registration.vo.cure.PatientCureInfoVo;
import com.yidian.registration.vo.cure.PatientCureOperateVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/4/3 22:27
 */
@Service
public class CureServiceImpl implements ICureService {

    Logger logger = LoggerFactory.getLogger(CureServiceImpl.class);

    @Autowired
    private TPatientCureDao patientCureDao;

    @Autowired
    private TPatientUserDao patientUserDao;


    @Override
    public PageVo<List<PatientCureInfoVo>> getCureList(String patientName, String mobile, Date cureDate, Integer pageNo, Integer pageSize) {
        logger.info("getCureList start, patientName:{}, mobile：{}, cureDate:{}, pageNo:{}, pageSize:{}", patientName, mobile, cureDate, pageNo, pageSize);
        PageVo<List<PatientCureInfoVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(pageNo);
        pageVo.setPageSize(pageSize);
        //
        List<Long> userIds = null;
        if (StringUtils.isNoneBlank(patientName) || StringUtils.isNoneBlank(mobile)) {
            List<TPatientUserEntity> users = patientUserDao.getPatientUserByNameAndMobile(patientName, mobile);
            if (CollectionUtils.isEmpty(users)) {
                return pageVo;
            }
            userIds = users.stream().map(TPatientUserEntity::getId).collect(Collectors.toList());
        }
        Long cureDateL = 0L;
        if (Objects.nonNull(cureDate)) {
            cureDateL = DateBuilder.toLongDate(cureDate);

        }
        //查询总量
        int configListTotal = patientCureDao.getCureListTotal(userIds, cureDateL);
        if (configListTotal <= 0) {
            return pageVo;
        }
        pageVo.setCount(configListTotal);

        int index = (pageNo - 1) * pageSize;
        List<TPatientCureEntity> configList = patientCureDao.getCureList(userIds, cureDateL, index, pageSize);
        if (CollectionUtils.isEmpty(configList)) {
            return pageVo;
        }
        List<PatientCureInfoVo> list = new ArrayList<>();
        for (TPatientCureEntity cure : configList) {
            PatientCureInfoVo vo = entityToVo(cure);
            //查询用户信息
            TPatientUserEntity userEntity = patientUserDao.selectInfoById(cure.getPid());
            if (Objects.nonNull(userEntity)) {
                vo.setPatientName(userEntity.getName());
                vo.setMobile(userEntity.getMobile());
            }
            list.add(vo);
        }
        pageVo.setData(list);
        logger.info("getCureList start, patientName:{}, cureDate:{}, pageNo:{}, pageSize:{}, pageVo:{}", patientName, cureDate, pageNo, pageSize, pageVo);
        return pageVo;
    }

    @Override
    public ResultVo<Boolean> addCure(PatientCureAddVo addVo) {
        logger.info("addCure start ,addVo:{}", addVo.toString());
        //根据pid查询用户信息
        TPatientUserEntity userEntity = patientUserDao.selectInfoById(addVo.getPid());
        if (Tools.isNull(userEntity)) {
            logger.info("addCure not get select user info ,addVo:{}", addVo.toString());
            return new ResultVo<>(StatusEnum.NOT_USER_INFO.getCode(), StatusEnum.NOT_USER_INFO.getDesc());
        }
        Long cureDateL = 0L;
        if (Objects.nonNull(addVo.getCureDate())) {
            cureDateL = DateBuilder.toLongDate(addVo.getCureDate());
        }
        ResultVo<Boolean> resultVo = new ResultVo();

        TPatientCureEntity cureEntity = new TPatientCureEntity();
        cureEntity.setPid(addVo.getPid());
        cureEntity.setItems(addVo.getItems());
        cureEntity.setCureDate(cureDateL);
        cureEntity.setCureTime(addVo.getCureTime());
        cureEntity.setCreateTime(new Date());
        int insert = patientCureDao.insert(cureEntity);
        if (insert > 0) {
            resultVo.setData(true);
        } else {
            resultVo.setData(false);
        }
        logger.info("addCure end ,addVo:{}, result:{}", addVo.toString(), resultVo);
        return resultVo;
    }

    @Override
    public PatientCureInfoVo getInfoByCid(Long cid) {
        logger.info("getInfoByCid start cid:{}", cid);
        TPatientCureEntity cure = patientCureDao.getCureById(cid);
        if (Objects.isNull(cure)) {
            return null;
        }
        PatientCureInfoVo vo = entityToVo(cure);
        //查询用户信息
        TPatientUserEntity userEntity = patientUserDao.selectInfoById(cure.getPid());
        if (Objects.nonNull(userEntity)) {
            vo.setPatientName(userEntity.getName());
            vo.setMobile(userEntity.getMobile());
        }
        logger.info("getInfoByCid end cid:{},vo:{}", cid, vo);
        return vo;
    }

    @Override
    public Boolean signIn(PatientCureOperateVo cureOperateVo) {
        logger.info("signIn start cureOperateVo:{}", cureOperateVo);

        //查询记录信息
        TPatientCureEntity cure = patientCureDao.getCureById(cureOperateVo.getCureId());
        if (Objects.isNull(cure)) {
            return false;
        }
        if (!PatientCureStateEnum.REGISTRATION.getCode().equals(cure.getStatus())) {
            return false;
        }
        //使用次数
        TPatientUserEntity userEntity = patientUserDao.selectInfoById(cureOperateVo.getPid());
        if (Objects.isNull(userEntity)) {
            return false;
        }
        int result = patientUserDao.updateRemainNum(cureOperateVo.getPid(), -cureOperateVo.getUseNum());
        if (result <= 0) {
            return false;
        }
        userEntity = patientUserDao.selectInfoById(cureOperateVo.getPid());

        cure.setStatus(PatientCureStateEnum.CURE.getCode());
        cure.setItems(cureOperateVo.getItems());
        cure.setRemainNum(userEntity.getRemainNum());
        cure.setUseNum(cureOperateVo.getUseNum());
        int update = patientCureDao.update(cure);
        if (update > 0) {
            return true;
        }
        logger.info("signIn start cureOperateVo:{}", cureOperateVo);
        return false;
    }

    @Override
    public ResultVo<Boolean> operation(Long cid, Integer status) {
        logger.info("operation start cid:{}, status", cid, status);
        if (!PatientCureStateEnum.CANCEL.getCode().equals(status) && !PatientCureStateEnum.NOT_COME.getCode().equals(status)) {
            return new ResultVo<>(StatusEnum.OPERATE_TYPE_FAIL.getCode(), StatusEnum.OPERATE_TYPE_FAIL.getDesc());
        }
        //查询记录信息
        TPatientCureEntity cure = patientCureDao.getCureById(cid);
        if (Objects.isNull(cure)) {
            return new ResultVo<>(StatusEnum.CURE_DATA_FAIL.getCode(), StatusEnum.CURE_DATA_FAIL.getDesc());
        }
        if (RegistrationOperationEnum.SIGN_IN.getCode() == cure.getStatus()) {
            return new ResultVo<>(StatusEnum.CURE_DATA_SGIN_IN.getCode(), StatusEnum.CURE_DATA_SGIN_IN.getDesc());
        }
        int result = patientCureDao.updateCureStatusById(cure.getId(), status, "");
        if (result > 0) {
            return new ResultVo<>(true);
        }
        return new ResultVo<>(false);
    }

    @Override
    public PageVo<List<PatientCureInfoVo>> getCureListByPid(Long pid, Integer pageNo, Integer pageSize) {
        logger.info("getCureListByPid start, pid:{}, pageNo:{}, pageSize:{}", pid, pageNo, pageSize);
        PageVo<List<PatientCureInfoVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(pageNo);
        pageVo.setPageSize(pageSize);

        //查询总量
        int configListTotal = patientCureDao.getCureListTotal(Collections.singletonList(pid), null);
        if (configListTotal <= 0) {
            return pageVo;
        }
        pageVo.setCount(configListTotal);

        int index = (pageNo - 1) * pageSize;
        List<TPatientCureEntity> configList = patientCureDao.getCureList(Collections.singletonList(pid), null, index, pageSize);
        if (CollectionUtils.isEmpty(configList)) {
            return pageVo;
        }
        List<PatientCureInfoVo> list = new ArrayList<>();
        for (TPatientCureEntity cure : configList) {
            PatientCureInfoVo vo = entityToVo(cure);
            list.add(vo);
        }
        pageVo.setData(list);
        logger.info("getCureListByPid start, pid:{}, pageNo:{}, pageSize:{}, pageVo:{}", pid, pageNo, pageSize, pageVo);
        return pageVo;
    }

    /**
     * entity to vo
     *
     * @param cureEntity
     * @return
     */
    private PatientCureInfoVo entityToVo(TPatientCureEntity cureEntity) {
        PatientCureInfoVo vo = new PatientCureInfoVo();
        vo.setId(cureEntity.getId());
        vo.setPid(cureEntity.getPid());
        vo.setCureDate(String.valueOf(cureEntity.getCureDate()));
        vo.setCureTime(cureEntity.getCureTime());
        vo.setStatus(cureEntity.getStatus());
        vo.setItems(cureEntity.getItems());
        vo.setCreateTime(DateBuilder.formatDate(cureEntity.getCreateTime(), DateBuilder.FORMAT_FULL));
        return vo;
    }
}
