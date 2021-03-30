package com.yidian.registration.controller;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.constant.Constants;
import com.yidian.registration.enums.StatusEnum;
import com.yidian.registration.service.IRegistrationService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.registration.ConfigAddVo;
import com.yidian.registration.vo.registration.ConfigVo;
import com.yidian.registration.vo.registration.RegistrationVo;
import com.yidian.registration.vo.req.RegistrationListReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @Author: QingHang
 * @Description: 预约管理
 * @Date: 2021/1/21 23:23
 */
@RestController
@RequestMapping("/registration/manager")
public class RegistrationManagerController {


    Logger logger = LoggerFactory.getLogger(RegistrationManagerController.class);

    @Autowired
    private IRegistrationService registrationService;

    /**
     * 获取预约列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getList", produces = "application/json;charset=UTF-8")
    public PageVo<List<RegistrationVo>> getList(RegistrationListReq req) {
        logger.info("[getList]获取预约列表,start，req={}", req);
        if (Objects.isNull(req)) {
            return new PageVo<>(StatusEnum.FAIL_CODE.getCode(), StatusEnum.FAIL_CODE.getDesc(), null);
        }
        if (Tools.isNull(req.getPage()) || req.getPage() <= 0) {
            req.setPage(Constants.DEFAULT_PAGE_NO);
        }
        if (Tools.isNull(req.getLimit()) || req.getLimit() <= 0) {
            req.setLimit(Constants.DEFAULT_PAGE_SIZE);
        }
        PageVo<List<RegistrationVo>> pageVos = registrationService.getRegistrationList(req.getName(), req.getMobile(), req.getDay(), req.getPage(), req.getLimit());
        logger.info("[getList]获取预约列表,end，req={}, res:{}", req, JSON.toJSON(pageVos));
        return pageVos;

    }

    /**
     * 查询注册详情
     *
     * @param id 用户id
     * @return
     */
//    @RequestMapping(value = "/getConfig", produces = "application/json;charset=UTF-8")
//    public ResultVo<ConfigVo> getConfig(Long id) {
//        logger.info("[getConfig]查询配置信息，id={}", id);
//        if (Tools.isNull(id) || id <= 0) {
//            logger.info("[getConfig]查询配置信息，参数存在空值");
//            return new ResultVo<>(-1, "请选择账号");
//        }
//        ConfigVo res = configService.getConfigById(id);
//        logger.info("[getConfig]查询配置信息,end,res={}", JSON.toJSON(res));
//        return new ResultVo<>(res);
//    }

    /**
     * 取消预约
     *
     * @param id 预约id
     * @param operationType 操作类型
     * @return
     */
    @RequestMapping(value = "/operation", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> operation(Long id, Integer operationType) {
        logger.info("[cancel]取消预约，id={}", id);
        if (Tools.isNull(id) || id <= 0) {
            logger.info("[cancel]取消预约，参数存在空值");
            return new ResultVo<>(-1, "请选择账号");
        }
        Boolean res = registrationService.operation(id, operationType);
        logger.info("[cancel]取消预约,end, res={}", JSON.toJSON(res));
        return new ResultVo<>(res);
    }


}
