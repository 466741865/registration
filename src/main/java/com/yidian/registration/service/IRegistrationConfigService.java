package com.yidian.registration.service;

import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.registration.ConfigAddVo;
import com.yidian.registration.vo.registration.ConfigVo;

import java.util.Date;
import java.util.List;

/**
 * @Author: QingHang
 * @Description: 预约配置service
 * @Date: 2021/1/21 23:24
 */
public interface IRegistrationConfigService {

    /**
     * 查询列表
     * @param date
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageVo<List<ConfigVo>> getConfigList(Date date, Integer pageNo , Integer pageSize);

    /**
     * 添加配置
     * @param addVo
     */
    Boolean addConfig(ConfigAddVo addVo);

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    ConfigVo getConfigById(Long id);

    /**
     * 刪除配置
     * @param id
     * @return
     */
    Boolean deleteConfig(Long id);


}
