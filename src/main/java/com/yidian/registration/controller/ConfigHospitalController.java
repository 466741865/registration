package com.yidian.registration.controller;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.constant.Constants;
import com.yidian.registration.service.IConfigHospitalService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.config.hospital.ConfigHospitalAddVo;
import com.yidian.registration.vo.config.hospital.ConfigHospitalDetailVo;
import com.yidian.registration.vo.config.hospital.ConfigHospitalUpdateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: QingHang
 * @Description: 医院配置
 * @Date: 2022/6/11 10:35
 */
@RestController
@RequestMapping("/config/hospital")
public class ConfigHospitalController {

    Logger logger = LoggerFactory.getLogger(ConfigHospitalController.class);

    @Resource
    private IConfigHospitalService configHospitalService;

    @RequestMapping(value = "/getList", produces = "application/json;charset=UTF-8")
    public PageVo<List<ConfigHospitalDetailVo>> getHospitalList(String hospitalName, Integer page, Integer limit) {
        logger.info("[getHospitalList]获取单位列表,start，hospitalName={}, pageNo={}, pageSize={}", hospitalName, page, limit);
        if (Tools.isNull(page) || page <= 0) {
            page = Constants.DEFAULT_PAGE_NO;
        }
        if (Tools.isNull(limit) || limit <= 0) {
            limit = Constants.DEFAULT_PAGE_SIZE;
        }
        PageVo<List<ConfigHospitalDetailVo>> pageVo = configHospitalService.getHospitalConfigList(hospitalName, page, limit);
        pageVo.setPageNum(page);
        pageVo.setPageSize(limit);
        logger.info("[getHospitalList]获取单位列表,end，hospitalName={}, pageNo={}, pageSize={}, res:{}", hospitalName, page, limit, JSON.toJSON(pageVo));
        return pageVo;

    }

    /**
     * 添加单位
     *
     * @param addVo 添加单位
     * @return
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> addHospital(ConfigHospitalAddVo addVo) {
        logger.info("[addHospital]添加单位，userAddVo={}", JSON.toJSON(addVo));
        if (Tools.isNull(addVo) || Tools.isNull(addVo.getHospitalName()) ||
                Tools.isNull(addVo.getBasicSalary())) {
            logger.info("[addHospital]添加单位，参数存在空值");
            return new ResultVo<>(-1, "请填写完整的信息");
        }
        Boolean res = configHospitalService.addHospitalConfig(addVo);
        logger.info("[addHospital]添加单位,end，userAddVo={},res={}", addVo.toString(), JSON.toJSON(res));
        return new ResultVo<>(res);
    }

    /**
     * 查询详情信息
     *
     * @param hid 用户id
     * @return
     */
    @RequestMapping(value = "/getInfo", produces = "application/json;charset=UTF-8")
    public ResultVo<ConfigHospitalDetailVo> getInfo(Long hid) {
        logger.info("[getInfo]查询详情信息，hid={}", hid);
        if (Tools.isNull(hid) || hid <= 0) {
            logger.info("[getInfo]查询详情信息，参数存在空值");
            return new ResultVo<>(-1, "请选择单位");
        }
        ConfigHospitalDetailVo configDeail = configHospitalService.getHospitalConfigDeail(hid);
        logger.info("[getInfo]查询详情信息,end,hid={},res={}", hid, JSON.toJSON(configDeail));
        return new ResultVo<>(configDeail);
    }

    /**
     * 修改信息
     *
     * @param updateVo
     * @return
     */
    @RequestMapping(value = "/update", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> updateInfo(ConfigHospitalUpdateVo updateVo) {
        logger.info("[updateInfo]修改信息，updateVo={}", updateVo);
        if (Tools.isNull(updateVo) || updateVo.getId() <= 0) {
            logger.info("[updateInfo]修改信息，参数存在空值");
            return new ResultVo<>(-1, "请选择单位");
        }
        boolean result = configHospitalService.updateHospitalConfig(updateVo);
        logger.info("[updateInfo]修改信息,end,updateVo={},res={}", updateVo, result);
        return new ResultVo<>(result);
    }

    /**
     * 删除
     *
     * @param hid 用户id
     * @return
     */
    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> delete(Long hid) {
        logger.info("[delete]删除信息，hid={}", hid);
        if (Tools.isNull(hid) || hid <= 0) {
            logger.info("[delete]删除信息，参数存在空值");
            return new ResultVo<>(-1, "请选择单位");
        }
        Boolean result = configHospitalService.deleteConfig(hid);
        logger.info("[delete]删除信息,end,hid={},res={}", hid, result);
        return new ResultVo<>(result);
    }


}
