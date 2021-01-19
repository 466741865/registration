package com.yidian.registration.service;

import com.yidian.registration.entity.SysRoleEntity;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.rolemanager.RoleInfoVo;

import java.util.List;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : TODO
 * @date : 2019/12/26 17:41
 **/
public interface IRoleService {


    SysRoleEntity getRoleInfoById(Long rid);

    /**
     * 查询角色列表
     * @param roleName
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageVo<List<RoleInfoVo>> getRoleList(String roleName, Integer pageNo, Integer pageSize);

    /**
     * 添加用户
     * @param roleName
     * @param remark
     * @return
     */
    ResultVo<Boolean> addRole(String roleName , String remark);

    /**
     * 编辑用户
     * @param rid
     * @param roleName
     * @param remark
     * @return
     */
    ResultVo<Boolean> updateRole(long rid ,String roleName , String remark);

    /**
     * 删除用户
     * @param rid
     */
    ResultVo<Boolean> deleteRole(Long rid);
}
