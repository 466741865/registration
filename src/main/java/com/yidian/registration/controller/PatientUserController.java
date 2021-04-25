package com.yidian.registration.controller;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.constant.Constants;
import com.yidian.registration.service.IPatientUserService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.patientuser.PatientUserAddVo;
import com.yidian.registration.vo.patientuser.PatientUserInfoVo;
import com.yidian.registration.vo.patientuser.PatientUserPayMentInfoVo;
import com.yidian.registration.vo.patientuser.PatientUserPayMentVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: QingHang
 * @Description: 病号用户信息
 * @Date: 2021/1/21 23:23
 */
@RestController
@RequestMapping("/patient/user")
public class PatientUserController {


    Logger logger = LoggerFactory.getLogger(PatientUserController.class);

    @Autowired
    private IPatientUserService patientUserService;

    /**
     * 获取用户列表
     *
     * @param patientName
     * @param mobile
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getPatientList", produces = "application/json;charset=UTF-8")
    public PageVo<List<PatientUserInfoVo>> getPatientList(String patientName, String mobile, Integer page, Integer limit) {
        logger.info("[getPatientList]获取挂号用户列表,start，patientName={},mobile={},pageNo={},pageSize={}", patientName, mobile, page, limit);
        if (Tools.isNull(page) || page <= 0) {
            page = Constants.DEFAULT_PAGE_NO;
        }
        if (Tools.isNull(limit) || limit <= 0) {
            limit = Constants.DEFAULT_PAGE_SIZE;
        }
        PageVo<List<PatientUserInfoVo>> pageVos = patientUserService.getPatientUserList(patientName, mobile, page, limit);
        pageVos.setPageNum(page);
        pageVos.setPageSize(limit);
        logger.info("[getPatientList]获取挂号用户列表,start，patientName={},mobile={},pageNo={},pageSize={}, res:{}", patientName, mobile, page, limit, JSON.toJSON(pageVos));
        return pageVos;

    }

    /**
     * 添加用户
     *
     * @param addVo 添加用户
     * @return
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> addpatientUser(PatientUserAddVo addVo) {
        logger.info("[addpatientUser]添加配置，userAddVo={}", JSON.toJSON(addVo));
        if (Tools.isNull(addVo) || Tools.isNull(addVo.getPatientName()) ||
                Tools.isNull(addVo.getMobile())) {
            logger.info("[addpatientUser]添加配置，参数存在空值");
            return new ResultVo<>(-1, "请填写完整的信息");
        }
        Boolean res = patientUserService.addPatientUser(addVo);
        logger.info("[addpatientUser]添加配置,end，userAddVo={},res={}", addVo.toString(), JSON.toJSON(res));
        return new ResultVo<>(res);
    }

    /**
     * 查询详情信息
     *
     * @param uid 用户id
     * @return
     */
    @RequestMapping(value = "/getInfo", produces = "application/json;charset=UTF-8")
    public ResultVo<PatientUserInfoVo> getInfo(Long uid) {
        logger.info("[getInfo]查询详情信息，uid={}", uid);
        if (Tools.isNull(uid) || uid <= 0) {
            logger.info("[getInfo]查询详情信息，参数存在空值");
            return new ResultVo<>(-1, "请选择账号");
        }
        PatientUserInfoVo res = patientUserService.getInfoByUid(uid);
        logger.info("[getInfo]查询详情信息,end,uid={},res={}", uid, JSON.toJSON(res));
        return new ResultVo<>(res);
    }


    /**
     * 用户缴费
     *
     * @param payMentVo 用户id
     * @return
     */
    @RequestMapping(value = "/payMent", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> payMent(PatientUserPayMentVo payMentVo) {
        logger.info("[payMent]用户缴费，payMentVo={}", payMentVo);
        if (Tools.isNull(payMentVo) || payMentVo.getPid() <= 0 || payMentVo.getBuyNum() <= 0 || StringUtils.isBlank(payMentVo.getPayMoney())) {
            logger.info("[payMent]用户缴费，参数存在空值");
            return new ResultVo<>(-1, "请选择账号");
        }
        boolean res = patientUserService.payMent(payMentVo);
        logger.info("[payMent]用户缴费,end,payMentVo={},res={}", payMentVo, res);
        return new ResultVo<>(res);
    }

    /**
     * 用户缴费信息列表
     * @param pid
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getPayMentList", produces = "application/json;charset=UTF-8")
    public PageVo<List<PatientUserPayMentInfoVo>> getPayMentList(Long pid, Integer page, Integer limit) {
        logger.info("[getPayMentList]用户缴费信息列表,start，pid={}, pageNo={}, pageSize={}", pid, page, limit);
        if (Tools.isNull(page) || page <= 0) {
            page = Constants.DEFAULT_PAGE_NO;
        }
        if (Tools.isNull(limit) || limit <= 0) {
            limit = Constants.DEFAULT_PAGE_SIZE;
        }
        PageVo<List<PatientUserPayMentInfoVo>> pageVos = patientUserService.getPatientPayMentList(pid, page, limit);
        pageVos.setPageNum(page);
        pageVos.setPageSize(limit);
        logger.info("[getPayMentList]用户缴费信息列表,start，pid={}, pageNo={} ,pageSize={}, res:{}", pid, page, limit, JSON.toJSON(pageVos));
        return pageVos;
    }

}
