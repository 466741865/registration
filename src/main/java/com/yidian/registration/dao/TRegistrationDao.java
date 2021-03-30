package com.yidian.registration.dao;

import com.yidian.registration.entity.TRegistration;
import com.yidian.registration.entity.TRegistrationConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TRegistrationDao {

    /**
     * 保存信息
     * @param record
     * @return
     */
    int insert(TRegistration record);

    /**
     * 分页查询挂号列表
     * @param name 姓名
     * @param mobile 手机号
     * @param day 日期
     * @param index 页数
     * @param pageSize 页大小
     * @return
     */
    List<TRegistration> getRegistrationList(@Param("name") String name, @Param("mobile") String mobile, @Param("day") Long day, @Param("index") int index, @Param("pageSize") int pageSize);

    /**
     * 查询挂号总数
     * @param name 姓名
     * @param mobile 手机号
     * @param day 日期
     * @return
     */
    int getRegistrationListTotal(@Param("name") String name, @Param("mobile") String mobile, @Param("day") Long day);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    TRegistration getRegistrationById(Long id);

    /**
     *  根据id修改预约配置状态
     * @param id
     * @param state
     * @return
     */
    int updateRegistrationStateById(@Param("id") Long id, @Param("state") int state);




}