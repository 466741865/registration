package com.yidian.registration.service;

import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.patientuser.PatientUserAddVo;
import com.yidian.registration.vo.patientuser.PatientUserInfoVo;
import com.yidian.registration.vo.patientuser.PatientUserPayMentInfoVo;
import com.yidian.registration.vo.patientuser.PatientUserPayMentVo;

import java.util.List;

/**
 * @Author: QingHang
 * @Description: 预约配置service
 * @Date: 2021/1/21 23:24
 */
public interface IPatientUserService {

    /**
     * 查询列表
     * @param patientName
     * @param mobile
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageVo<List<PatientUserInfoVo>> getPatientUserList(String patientName, String mobile, Integer pageNo , Integer pageSize);

    /**
     * 添加配置
     * @param addVo
     */
    Boolean addPatientUser(PatientUserAddVo addVo);

    /**
     * 根据id查询详情
     * @param uid
     * @return
     */
    PatientUserInfoVo getInfoByUid(Long uid);

    /**
     * 用户充值
     * @param payMentVo
     * @return
     */
    Boolean payMent(PatientUserPayMentVo payMentVo);

    /**
     * 缴费记录
     * @param pid
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageVo<List<PatientUserPayMentInfoVo>> getPatientPayMentList(Long pid, Integer pageNo , Integer pageSize);

}
