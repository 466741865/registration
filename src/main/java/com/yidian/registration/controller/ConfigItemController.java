package com.yidian.registration.controller;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.constant.Constants;
import com.yidian.registration.service.IConfigItemService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.config.item.ConfigItemAddVo;
import com.yidian.registration.vo.config.item.ConfigItemDetailVo;
import com.yidian.registration.vo.config.item.ConfigItemUpdateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Author: QingHang
 * @Description: 项目配置
 * @Date: 2022/6/11 10:35
 */
@RestController
@RequestMapping("/config/item")
public class ConfigItemController {

    Logger logger = LoggerFactory.getLogger(ConfigItemController.class);

    @Resource
    private IConfigItemService configItemService;

    @RequestMapping(value = "/getList", produces = "application/json;charset=UTF-8")
    public PageVo<List<ConfigItemDetailVo>> getItemList(String itemName, Integer page, Integer limit) {
        logger.info("[getItemList]获取项目列表,start，itemName={}, pageNo={}, pageSize={}", itemName, page, limit);
        if (Tools.isNull(page) || page <= 0) {
            page = Constants.DEFAULT_PAGE_NO;
        }
        if (Tools.isNull(limit) || limit <= 0) {
            limit = Constants.DEFAULT_PAGE_SIZE;
        }
        PageVo<List<ConfigItemDetailVo>> pageVo = configItemService.getItemConfigList(itemName, page, limit);
        pageVo.setPageNum(page);
        pageVo.setPageSize(limit);
        logger.info("[getItemList]获取项目列表,end，itemName={}, pageNo={}, pageSize={}, res:{}", itemName, page, limit, JSON.toJSON(pageVo));
        return pageVo;
    }

    /**
     * 添加项目
     *
     * @param addVo 添加项目
     * @return
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> addItem(ConfigItemAddVo addVo) {
        logger.info("[addItem]添加项目，userAddVo={}", JSON.toJSON(addVo));
        if (Tools.isNull(addVo) || Tools.isNull(addVo.getItemName())
                || Tools.isNull(addVo.getCommission())
                || Tools.isNull(addVo.getHospitalId())) {
            logger.info("[addItem]添加项目，参数存在空值");
            return new ResultVo<>(-1, "请填写完整的信息");
        }
        Boolean res = configItemService.addItemConfig(addVo);
        logger.info("[addItem]添加项目,end，userAddVo={},res={}", addVo.toString(), JSON.toJSON(res));
        return new ResultVo<>(res);
    }

    /**
     * 查询详情信息
     *
     * @param cid 项目id
     * @return
     */
    @RequestMapping(value = "/getInfo", produces = "application/json;charset=UTF-8")
    public ResultVo<ConfigItemDetailVo> getInfo(Long cid) {
        logger.info("[getInfo]查询详情信息，cid={}", cid);
        if (Tools.isNull(cid) || cid <= 0) {
            logger.info("[getInfo]查询详情信息，参数存在空值");
            return new ResultVo<>(-1, "请选择单位");
        }
        ConfigItemDetailVo configDeail = configItemService.getItemConfigDeail(cid);
        logger.info("[getInfo]查询详情信息,end,hid={},res={}", cid, JSON.toJSON(configDeail));
        return new ResultVo<>(configDeail);
    }

    /**
     * 修改信息
     *
     * @param updateVo
     * @return
     */
    @PostMapping(value = "/update", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> updateInfo(ConfigItemUpdateVo updateVo) {
        logger.info("[updateInfo]修改信息，updateVo={}", updateVo);
        if (Tools.isNull(updateVo) || updateVo.getId() <= 0) {
            logger.info("[updateInfo]修改信息，参数存在空值");
            return new ResultVo<>(-1, "请选择单位");
        }
        boolean result = configItemService.updateItemConfig(updateVo);
        logger.info("[updateInfo]修改信息,end,updateVo={},res={}", updateVo, result);
        return new ResultVo<>(result);
    }

    /**
     * 删除
     *
     * @param cid 项目id
     * @return
     */
    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> delete(Long cid) {
        logger.info("[delete]删除信息，cid={}", cid);
        if (Tools.isNull(cid) || cid <= 0) {
            logger.info("[delete]删除信息，参数存在空值");
            return new ResultVo<>(-1, "请选择项目");
        }
        Boolean result = configItemService.deleteConfig(cid);
        logger.info("[delete]删除信息,end,cid={},res={}", cid, result);
        return new ResultVo<>(result);
    }

    /**
     * 根据医院id查询项目列表
     * @param hid
     * @return
     */
    @RequestMapping(value = "/getListByHid", produces = "application/json;charset=UTF-8")
    public List<ConfigItemDetailVo> getItemListByHid(Long hid) {
        logger.info("[getItemListByHid]获取项目列表,start，hid={}", hid);
        if (Tools.isNull(hid) || hid <= 0) {
            logger.info("[getItemListByHid]获取项目列表，参数存在空值");
            return Collections.emptyList();
        }
        List<ConfigItemDetailVo> itemConfigList = configItemService.getItemConfigListByHid(hid);
        logger.info("[getItemListByHid]获取项目列表,end，hid={}, res:{}", hid, JSON.toJSON(itemConfigList));
        return itemConfigList;
    }


}
