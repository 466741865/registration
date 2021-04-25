package com.yidian.registration.service;

import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.cure.PatientCureAddVo;
import com.yidian.registration.vo.cure.PatientCureInfoVo;
import com.yidian.registration.vo.cure.PatientCureOperateVo;
import com.yidian.registration.vo.patientuser.PatientUserAddVo;
import com.yidian.registration.vo.patientuser.PatientUserInfoVo;

import java.util.Date;
import java.util.List;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/4/3 22:27
 */
public interface ICureService {

    /**
     * 查询列表
     * @param patientName
     * @param cureDate
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageVo<List<PatientCureInfoVo>> getCureList(String patientName, String mobile, Date cureDate, Integer pageNo , Integer pageSize);

    /**
     * 挂号
     * @param addVo
     */
    ResultVo<Boolean> addCure(PatientCureAddVo addVo);

    /**
     * 根据id查询详情
     * @param cid
     * @return
     */
    PatientCureInfoVo getInfoByCid(Long cid);

    /**
     * 签到
     * @param cureOperateVo
     * @return
     */
    Boolean signIn(PatientCureOperateVo cureOperateVo);

    /**
     * 操作
     * @return
     */
    ResultVo<Boolean> operation(Long cid, Integer status);

    /**
     * 根据pid查询预约列表
     * @param pid
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageVo<List<PatientCureInfoVo>> getCureListByPid(Long pid, Integer pageNo , Integer pageSize);



}
