package com.yidian.registration.service;

import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.config.item.ConfigItemAddVo;
import com.yidian.registration.vo.config.item.ConfigItemDetailVo;
import com.yidian.registration.vo.config.item.ConfigItemUpdateVo;

import java.util.List;

/**
 * @Author: QingHang
 * @Description: 项目配置
 * @Date: 2022/6/11 10:31
 */
public interface IConfigItemService {

    /**
     * 查询配置列表
     * @param name
     * @return
     */
    PageVo<List<ConfigItemDetailVo>> getItemConfigList(String name, Integer pageNo, Integer pageSize);

    /**
     * 查询配置详情
     * @param id
     * @return
     */
    ConfigItemDetailVo getItemConfigDeail(Long id);

    /**
     * 添加配置
     * @param addVo
     * @return
     */
    boolean addItemConfig(ConfigItemAddVo addVo);

    /**
     * 更新
     * @param updateVo
     * @return
     */
    boolean updateItemConfig(ConfigItemUpdateVo updateVo);

    /**
     * 删除配置
     * @param id
     * @return
     */
    boolean deleteConfig(Long id);

    /**
     * 根据医院id查询项目列表
     * @param hid
     * @return
     */
    List<ConfigItemDetailVo> getItemConfigListByHid(Long hid);
}
