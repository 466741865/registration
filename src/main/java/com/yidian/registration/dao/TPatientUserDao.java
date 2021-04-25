package com.yidian.registration.dao;

import com.yidian.registration.entity.TPatientUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TPatientUserDao {


    int insert(TPatientUserEntity record);

    TPatientUserEntity selectInfoById(Long id);

    /**
     * 列表
     *
     * @param name
     * @param mobile
     * @param index
     * @param pageSize
     * @return
     */
    List<TPatientUserEntity> getPatientUserList(@Param("name") String name, @Param("mobile") String mobile, @Param("index") int index, @Param("pageSize") int pageSize);

    /**
     * 查询list总量
     *
     * @param name
     * @return
     */
    int getPatientUserListTotal(@Param("name") String name, @Param("mobile") String mobile);

    /**
     * 根据名称模糊查询
     *
     * @param name
     * @return
     */
    List<TPatientUserEntity> getUsersByName(@Param("name") String name);


    /**
     * 根据手机号、姓名查询
     *
     * @param name
     * @return
     */
    List<TPatientUserEntity> getPatientUserByNameAndMobile(@Param("name") String name, @Param("mobile") String mobile);

    /**
     * 更新次数
     * @param pid
     * @param num
     * @return
     */
    int updateRemainNum(@Param("pid") Long pid, @Param("num") int num);

}