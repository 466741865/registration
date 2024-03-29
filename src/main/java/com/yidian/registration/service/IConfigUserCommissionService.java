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
     * @param belongId
     * @return
     */
    PageVo<List<ConfigUserCommissionDeatilVo>> getUserCommissionConfigList(Long belongId, Integer pageNo, Integer pageSize);

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

    /**
     * 根据项目Id获取提成人员列表
     * @param iid
     * @return
     */
    List<ConfigUserCommissionDeatilVo>  getUserCommissionConfigListByIid(Long iid);

    /**
     * 查询指定的项目提成
     * @param belongId
     * @param hospitalId
     * @param itemId
     * @return
     */
    List<ConfigUserCommissionDeatilVo> getUserCommissionConfig(Long belongId, Long hospitalId, Long itemId);

    /**
     * 提成范围校验
     * @param hospitalId
     * @param itemId
     * @param commission
     * @return
     */
    boolean checkUserCommission(Long hospitalId, Long itemId, String commission);

}
