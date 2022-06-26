package com.yidian.registration.service;


import com.yidian.registration.entity.SysUserEntity;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.usermanager.UserAddVo;
import com.yidian.registration.vo.usermanager.UserInfoVo;

import java.util.List;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : TODO
 * @date : 2019/12/26 17:41
 **/
public interface IUserService {

    SysUserEntity getUserInfoById(Long uid);

    SysUserEntity getUserInfoByAccount(String userName, String password);

    SysUserEntity getUserInfoByName(String userName);

    /**
     *
     * @param mobile
     * @param realName
     * @param state
     * @return
     */
    PageVo<List<UserInfoVo>> getUserList(String mobile, String realName, Integer state, Integer pageNo , Integer pageSize);

    /**
     * 添加用户
     * @param userAddVo
     */
    ResultVo<Boolean> addUser(UserAddVo userAddVo);

    /**
     * 编辑用户
     * @param userAddVo
     */
    ResultVo<Boolean> updateUser(UserAddVo userAddVo);

    /**
     * 编辑用户状态
     * @param uid
     */
    ResultVo<Boolean> updateUserState(Long  uid);

    /**
     * 删除用户
     * @param uid
     */
    ResultVo<Boolean> delete(Long  uid);
}
