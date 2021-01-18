package com.yidian.registration.dao;

import com.yidian.registration.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleDao {


    int insert(SysRoleEntity record);

    List<SysRoleEntity> selectAll();

    SysRoleEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SysRoleEntity record);

    /**
     * 根据条件查询用户列表
     * @param roleName
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SysRoleEntity> getRoleList(@Param("roleName") String roleName, @Param("pageNum") Integer pageNum , @Param("pageSize") Integer pageSize);

    /**
     * 根据条件查询用户数
     * @param roleName
     * @return
     */
    int getRoleListCount(@Param("roleName") String roleName);

    int deleteByPrimaryKey(Long id);


}