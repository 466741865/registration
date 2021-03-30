package com.yidian.registration.controller;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.constant.Constants;
import com.yidian.registration.service.IRegistrationConfigService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.registration.ConfigAddVo;
import com.yidian.registration.vo.registration.ConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Author: QingHang
 * @Description: 预约配置
 * @Date: 2021/1/21 23:23
 */
@RestController
@RequestMapping("/registration/config")
public class RegistrationConfigController {


    Logger logger = LoggerFactory.getLogger(RegistrationConfigController.class);

    @Autowired
    private IRegistrationConfigService configService;

    /**
     * 获取预约配置列表
     *
     * @param startDate
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getConfigList", produces = "application/json;charset=UTF-8")
    public PageVo<List<ConfigVo>> getConfigList(Date startDate, Integer page, Integer limit) {
        logger.info("[getConfigList]获取预约配置列表,start，startDate={},pageNo={},pageSize={}", startDate, page, limit);
        if (Tools.isNull(page) || page <= 0) {
            page = Constants.DEFAULT_PAGE_NO;
        }
        if (Tools.isNull(limit) || limit <= 0) {
            limit = Constants.DEFAULT_PAGE_SIZE;
        }
        PageVo<List<ConfigVo>> pageVos = configService.getConfigList(startDate, page, limit);
        pageVos.setPageNum(page);
        pageVos.setPageSize(limit);
        logger.info("[getUserList]获取预约配置列表,end，startDate={},pageNo={},pageSize={}, res:{}", startDate, page, limit, JSON.toJSON(pageVos));
        return pageVos;

    }

    /**
     * 添加配置
     *
     * @param configAddVo 添加配置
     * @return
     */
    @RequestMapping(value = "/addConfig", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> addConfig(ConfigAddVo configAddVo) {
        logger.info("[addConfig]添加配置，userAddVo={}", JSON.toJSON(configAddVo));
        if (Tools.isNull(configAddVo) || Tools.isNull(configAddVo.getDate()) ||
                Tools.isNull(configAddVo.getType()) || Tools.isNull(configAddVo.getDuration())) {
            logger.info("[addConfig]添加配置，参数存在空值");
            return new ResultVo<>(-1, "请填写完整的信息");
        }
        Boolean res = configService.addConfig(configAddVo);
        logger.info("[addConfig]添加配置,end，configAddVo={},res={}", configAddVo.toString(), JSON.toJSON(res));
        return new ResultVo<>(res);
    }

    /**
     * 查询配置信息
     *
     * @param id 用户id
     * @return
     */
    @RequestMapping(value = "/getConfig", produces = "application/json;charset=UTF-8")
    public ResultVo<ConfigVo> getConfig(Long id) {
        logger.info("[getConfig]查询配置信息，id={}", id);
        if (Tools.isNull(id) || id <= 0) {
            logger.info("[getConfig]查询配置信息，参数存在空值");
            return new ResultVo<>(-1, "请选择账号");
        }
        ConfigVo res = configService.getConfigById(id);
        logger.info("[getConfig]查询配置信息,end,res={}", JSON.toJSON(res));
        return new ResultVo<>(res);
    }

    /**
     * 删除配置
     *
     * @param id 用户id
     * @return
     */
    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> delete(Long id) {
        logger.info("[delete]删除配置，id={}", id);
        if (Tools.isNull(id) || id <= 0) {
            logger.info("[delete]删除配置，参数存在空值");
            return new ResultVo<>(-1, "请选择账号");
        }
        Boolean res = configService.deleteConfig(id);
        logger.info("[delete]删除配置,end, res={}", JSON.toJSON(res));
        return new ResultVo<>(res);
    }


}
