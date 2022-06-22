package com.yidian.registration.service;

import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.config.user.*;

import java.util.List;

/**
 * @Author: QingHang
 * @Description: 项目配置
 * @Date: 2022/6/11 10:31
 */
public interface IConfigUserService {

    /**
     * 查询配置列表
     * @param name
     * @return
     */
    PageVo<List<ConfigUserDeatilVo>> getUserConfigList(String name, Integer type, Integer pageNo, Integer pageSize);

    /**
     * 查询配置详情
     * @param id
     * @return
     */
    ConfigUserDeatilVo getUserConfigDeail(Long id);

    /**
     * 添加配置
     * @param addVo
     * @return
     */
    boolean addUserConfig(ConfigUserAddVo addVo);

    /**
     * 更新
     * @param updateVo
     * @return
     */
    boolean updateUserConfig(ConfigUserUpdateVo updateVo);

    /**
     * 删除配置
     * @param id
     * @return
     */
    boolean deleteConfig(Long id);

}
