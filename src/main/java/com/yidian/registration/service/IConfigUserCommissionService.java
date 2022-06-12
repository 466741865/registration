package com.yidian.registration.service;

import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.config.user.ConfigUserCommissionAddVo;
import com.yidian.registration.vo.config.user.ConfigUserCommissionDeatilVo;
import com.yidian.registration.vo.config.user.ConfigUserCommissionUpdateVo;

import java.util.List;

/**
 * @Author: QingHang
 * @Description: 项目配置
 * @Date: 2022/6/11 10:31
 */
public interface IConfigUserCommissionService {

    /**
     * 查询配置列表
     * @param name
     * @return
     */
    PageVo<List<ConfigUserCommissionDeatilVo>> getUserCommissionConfigList(String name, Integer pageNo, Integer pageSize);

    /**
     * 查询配置详情
     * @param id
     * @return
     */
    ConfigUserCommissionDeatilVo getUserCommissionConfigDeail(Long id);

    /**
     * 添加配置
     * @param addVo
     * @return
     */
    boolean addUserCommissionConfig(ConfigUserCommissionAddVo addVo);

    /**
     * 更新
     * @param updateVo
     * @return
     */
    boolean updateUserCommissionConfig(ConfigUserCommissionUpdateVo updateVo);

    /**
     * 删除配置
     * @param id
     * @return
     */
    boolean deleteConfig(Long id);

}
