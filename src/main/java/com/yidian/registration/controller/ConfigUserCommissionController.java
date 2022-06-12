package com.yidian.registration.controller;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.constant.Constants;
import com.yidian.registration.service.IConfigUserCommissionService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.config.user.ConfigUserCommissionAddVo;
import com.yidian.registration.vo.config.user.ConfigUserCommissionDeatilVo;
import com.yidian.registration.vo.config.user.ConfigUserCommissionUpdateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
    public PageVo<List<ConfigUserCommissionDeatilVo>> getUserCommissionList(String name, Integer page, Integer limit) {
        logger.info("[getUserCommissionList]获取用户列表,start，name={}, pageNo={}, pageSize={}", name, page, limit);
        if (Tools.isNull(page) || page <= 0) {
            page = Constants.DEFAULT_PAGE_NO;
        }
        if (Tools.isNull(limit) || limit <= 0) {
            limit = Constants.DEFAULT_PAGE_SIZE;
        }
        PageVo<List<ConfigUserCommissionDeatilVo>> pageVo = configUserCommissionService.getUserCommissionConfigList(name, page, limit);
        pageVo.setPageNum(page);
        pageVo.setPageSize(limit);
        logger.info("[getUserCommissionList]获取用户列表,end，name={}, pageNo={}, pageSize={}, res:{}", name, page, limit, JSON.toJSON(pageVo));
        return pageVo;

    }

    /**
     * 添加用户
     *
     * @param addVo 添加用户
     * @return
     */
    @PostMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> addUserCommission(ConfigUserCommissionAddVo addVo) {
        logger.info("[addUserCommission]添加用户，userAddVo={}", JSON.toJSON(addVo));
        if (Tools.isNull(addVo) || Tools.isNull(addVo.getName())
                || Tools.isNull(addVo.getCommission())
                || Tools.isNull(addVo.getItemId())) {
            logger.info("[addUserCommission]添加用户，参数存在空值");
            return new ResultVo<>(-1, "请填写完整的信息");
        }
        Boolean res = configUserCommissionService.addUserCommissionConfig(addVo);
        logger.info("[addUserCommission]添加用户,end，userAddVo={},res={}", addVo.toString(), JSON.toJSON(res));
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
            return new ResultVo<>(-1, "请选择单位");
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
        logger.info("[updateInfo]修改信息，updateVo={}", updateVo);
        if (Tools.isNull(updateVo) || updateVo.getId() <= 0) {
            logger.info("[updateInfo]修改信息，参数存在空值");
            return new ResultVo<>(-1, "请选择单位");
        }
        boolean result = configUserCommissionService.updateUserCommissionConfig(updateVo);
        logger.info("[updateInfo]修改信息,end,updateVo={},res={}", updateVo, result);
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
            return new ResultVo<>(-1, "请选择单位");
        }
        Boolean result = configUserCommissionService.deleteConfig(uid);
        logger.info("[delete]删除信息,end,uid={},res={}", uid, result);
        return new ResultVo<>(result);
    }


}
