package com.yidian.registration.dao;

import com.yidian.registration.entity.SysUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserEntity record);

    SysUserEntity selectByPrimaryKey(Long id);

    List<SysUserEntity> selectAll();

    int updateByPrimaryKey(SysUserEntity record);

    SysUserEntity selectByUserNameAndPwd(@Param("username") String userName,@Param("password") String password);

    /**
     * 根据条件查询用户列表
     * @param userName
     * @param realName
     * @param state
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SysUserEntity> getUserList(@Param("userName") String userName,@Param("realName") String realName, @Param("state") Integer state,@Param("pageNum") Integer pageNum ,@Param("pageSize") Integer pageSize);

    /**
     * 根据条件查询用户数
     * @param userName
     * @param realName
     * @param state
     * @return
     */
    int getUserListCount(@Param("userName") String userName,@Param("realName") String realName, @Param("state") Integer state);
}