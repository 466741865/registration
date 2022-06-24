package com.yidian.registration.controller;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.constant.Constants;
import com.yidian.registration.enums.StatusEnum;
import com.yidian.registration.service.IConfigUserCommissionService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.config.user.ConfigUserCommissionAddVo;
import com.yidian.registration.vo.config.user.ConfigUserCommissionDeatilVo;
import com.yidian.registration.vo.config.user.ConfigUserCommissionUpdateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: QingHang
 * @Description: 项目配置
 * @Date: 2022/6/11 10:35
 */
@RestController
@RequestMapping("/config/user/commission")
public class ConfigUserCommissionController {

    Logger logger = LoggerFactory.getLogger(ConfigUserCommissionController.class);

    @Resource
    private IConfigUserCommissionService configUserCommissionService;

    @RequestMapping(value = "/getList", produces = "application/json;charset=UTF-8")
    public PageVo<List<ConfigUserCommissionDeatilVo>> getUserCommissionList(Long belongId, Integer page, Integer limit) {
        logger.info("[getUserCommissionList]获取用户提成项目列表,start，BelongId={}, pageNo={}, pageSize={}", belongId, page, limit);
        if (Tools.isNull(page) || page <= 0) {
            page = Constants.DEFAULT_PAGE_NO;
        }
        if (Tools.isNull(limit) || limit <= 0) {
            limit = Constants.DEFAULT_PAGE_SIZE;
        }
        PageVo<List<ConfigUserCommissionDeatilVo>> pageVo = configUserCommissionService.getUserCommissionConfigList(belongId, page, limit);
        pageVo.setPageNum(page);
        pageVo.setPageSize(limit);
        logger.info("[getUserCommissionList]获取用户提成项目列表,end，BelongId={}, pageNo={}, pageSize={}, res:{}", belongId, page, limit, JSON.toJSON(pageVo));
        return pageVo;
    }

    /**
     * 添加用户
     *
     * @param addVo 添加用户
     * @return
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> addUserCommission(ConfigUserCommissionAddVo addVo) {
        logger.info("[addUserCommission]添加用户项目提成，userAddVo={}", JSON.toJSON(addVo));
        if (Tools.isNull(addVo) || Tools.isNull(addVo.getBelongId())
                || Tools.isNull(addVo.getCommission())
                || Tools.isNull(addVo.getHospitalId())
                || Tools.isNull(addVo.getItemId())) {
            logger.info("[addUserCommission]添加用户项目提成，参数存在空值");
            return new ResultVo<>(-1, "请填写完整的信息");
        }
        //校验
        boolean checkUserCommission = configUserCommissionService.checkUserCommission(addVo.getHospitalId(), addVo.getItemId(), addVo.getCommission());
        if(!checkUserCommission){
            logger.info("[addUserCommission]添加用户项目提成,个人提成比例不能超过医院提成比例 ,end，userAddVo={}", addVo.toString());
            return new ResultVo<>(StatusEnum.NODATA_CODE.getCode(), "个人提成比例不能超过医院提成比例");
        }
        List<ConfigUserCommissionDeatilVo> configs = configUserCommissionService.getUserCommissionConfig(addVo.getBelongId(), addVo.getHospitalId(), addVo.getItemId());
        if (!CollectionUtils.isEmpty(configs)) {
            logger.info("[addUserCommission]添加用户项目提成, 已存在相同配置,end，userAddVo={}", addVo.toString());
            return new ResultVo<>(StatusEnum.NODATA_CODE.getCode(), "已存在相同的配置，不可重复添加");
        }
        Boolean res = configUserCommissionService.addUserCommissionConfig(addVo);
        logger.info("[addUserCommission]添加用户项目提成,end，userAddVo={},res={}", addVo.toString(), JSON.toJSON(res));
        return new ResultVo<>(res);
    }

    /**
     * 查询详情信息
     *
     * @param uid 用户id
     * @return
     */
    @RequestMapping(value = "/getInfo", produces = "application/json;charset=UTF-8")
    public ResultVo<ConfigUserCommissionDeatilVo> getInfo(Long uid) {
        logger.info("[getInfo]查询详情信息，uid={}", uid);
        if (Tools.isNull(uid) || uid <= 0) {
            logger.info("[getInfo]查询详情信息，参数存在空值");
            return new ResultVo<>(-1, "请选择人员提成");
        }
        ConfigUserCommissionDeatilVo configDeail = configUserCommissionService.getUserCommissionConfigDeail(uid);
        logger.info("[getInfo]查询详情信息,end,uid={},res={}", uid, JSON.toJSON(configDeail));
        return new ResultVo<>(configDeail);
    }

    /**
     * 修改信息
     *
     * @param updateVo
     * @return
     */
    @PostMapping(value = "/update", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> updateInfo(ConfigUserCommissionUpdateVo updateVo) {
        logger.info("[updateInfo]修改用户项目提成，updateVo={}", updateVo);
        if (Tools.isNull(updateVo) || updateVo.getId() <= 0
                || Tools.isNull(updateVo.getBelongId())
                || Tools.isNull(updateVo.getCommission())
                || Tools.isNull(updateVo.getHospitalId())
                || Tools.isNull(updateVo.getItemId())) {
            logger.info("[updateInfo]修改用户项目提成，参数存在空值");
            return new ResultVo<>(-1, "请选择列表信息");
        }
        //校验
        boolean checkUserCommission = configUserCommissionService.checkUserCommission(updateVo.getHospitalId(), updateVo.getItemId(), updateVo.getCommission());
        if(!checkUserCommission){
            logger.info("[updateInfo]修改用户项目提成,个人提成比例不能超过医院提成比例 ,end，userAddVo={}", updateVo.toString());
            return new ResultVo<>(StatusEnum.NODATA_CODE.getCode(), "个人提成比例不能超过医院提成比例");
        }
        List<ConfigUserCommissionDeatilVo> configs = configUserCommissionService.getUserCommissionConfig(updateVo.getBelongId(), updateVo.getHospitalId(), updateVo.getItemId());
        if (!CollectionUtils.isEmpty(configs)) {
            configs = configs.stream().filter(config -> !config.getId().equals(updateVo.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(configs)) {
                logger.info("[updateInfo]修改用户项目提成, 已存在相同配置,end，userAddVo={}", updateVo.toString());
                return new ResultVo<>(StatusEnum.NODATA_CODE.getCode(), "已存在相同的配置，请修改后再提交");
            }
        }
        boolean result = configUserCommissionService.updateUserCommissionConfig(updateVo);
        logger.info("[updateInfo]修改用户项目提成,end,updateVo={},res={}", updateVo, result);
        return new ResultVo<>(result);
    }

    /**
     * 删除
     *
     * @param uid 用户id
     * @return
     */
    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> delete(Long uid) {
        logger.info("[delete]删除信息，uid={}", uid);
        if (Tools.isNull(uid) || uid <= 0) {
            logger.info("[delete]删除信息，参数存在空值");
            return new ResultVo<>(-1, "请选择人员");
        }
        Boolean result = configUserCommissionService.deleteConfig(uid);
        logger.info("[delete]删除信息,end,uid={},res={}", uid, result);
        return new ResultVo<>(result);
    }

    /**
     * 根据项目id查询项目列表
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/getListByIid", produces = "application/json;charset=UTF-8")
    public List<ConfigUserCommissionDeatilVo> getUserCommissionListByIid(Long itemId) {
        logger.info("[getUserCommissionListByIid]获取提成人员列表,start，iid={}", itemId);
        if (Tools.isNull(itemId) || itemId <= 0) {
            logger.info("[getUserCommissionListByIid]获取提成人员列表，参数存在空值");
            return Collections.emptyList();
        }
        List<ConfigUserCommissionDeatilVo> userCommissionDeatilVos = configUserCommissionService.getUserCommissionConfigListByIid(itemId);
        logger.info("[getUserCommissionListByIid]获取提成人员列表,end，itemId={}, res:{}", itemId, JSON.toJSON(userCommissionDeatilVos));
        return userCommissionDeatilVos;
    }


}
