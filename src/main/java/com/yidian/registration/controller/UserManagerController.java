package com.yidian.registration.controller;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.service.IUserService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.usermanager.UserAddVo;
import com.yidian.registration.vo.usermanager.UserInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : 用户管理
 * @date : 2020/1/11 15:27
 **/
@RestController
@RequestMapping("/user/manager")
public class UserManagerController {


    Logger logger = LoggerFactory.getLogger(UserManagerController.class);

    @Autowired
    private IUserService userService;

    /**
     * 获取用户列表
     * @param mobile
     * @param realName
     * @param state
     * @return
     */
    @RequestMapping(value = "/getUserList", produces="application/json;charset=UTF-8")
    public PageVo<List<UserInfoVo>> getUserList(String mobile, String realName, Integer state , Integer page , Integer limit){
        logger.info("[getUserList]根据条件查询用户列表,start，mobile={},realName={},state={},pageNo={},pageSize={}", mobile, realName, state, page, limit);
        if(Tools.isNull(page) || page <= 0 ){
            page = 1;
        }if(Tools.isNull(limit) || limit <= 0){
            limit = 10;
        }
        PageVo<List<UserInfoVo>> pageVos = userService.getUserList(mobile,realName,state,page,limit);
        pageVos.setPageNum(page);
        pageVos.setPageSize(limit);
        logger.info("[getUserList]根据条件查询用户列表,end，mobile={},realName={},state={} ,res={}", mobile, realName, state, JSON.toJSON(pageVos));
        return pageVos;

    }

    /**
     * 添加用户
     * @param userAddVo 用户信息
     * @return
     */
    @PostMapping(value = "/addUser", produces="application/json;charset=UTF-8")
    public ResultVo<Boolean> addUser(UserAddVo userAddVo){
        logger.info("[addUser]添加用户，userAddVo={}", userAddVo.toString());
        if(Tools.isNull(userAddVo) || Tools.isNull(userAddVo.getUserName()) || Tools.isNull(userAddVo.getRealName()) || Tools.isNull(userAddVo.getPwd()) || Tools.isNull(userAddVo.getPhone()) || Tools.isNull(userAddVo.getEmail())){
            logger.info("[addUser]添加用户，参数存在空值");
            return new ResultVo<>(-1,"请填写完整的信息");
        }

        ResultVo<Boolean> res = userService.addUser(userAddVo);
        logger.info("[addUser]添加用户,end，userAddVo={},res={}", userAddVo.toString(), JSON.toJSON(res));
        return res;
    }

    /**
     * 更改用户状态
     * @param uid 用户id
     * @return
     */
    @RequestMapping(value = "/updateState", produces="application/json;charset=UTF-8")
    public ResultVo<Boolean> updateState(Long uid){
        logger.info("[updateState]更改用户状态，uid={}", uid);
        if(Tools.isNull(uid) || uid <= 0){
            logger.info("[updateState]更改用户状态，参数存在空值");
            return new ResultVo<>(-1,"请选择账号");
        }
        ResultVo<Boolean> res = userService.updateUserState(uid);
        logger.info("[updateState]更改用户状态,end,res={}", JSON.toJSON(res));
        return res;
    }

    /**
     * 更改用户信息
     * @param userAddVo 用户id
     * @return
     */
    @RequestMapping(value = "/updateUserInfo", produces="application/json;charset=UTF-8")
    public ResultVo<Boolean> updateUserInfo(UserAddVo userAddVo){
        logger.info("[updateUserInfo]更改用户信息，userAddVo={}", userAddVo);
        if(Tools.isNull(userAddVo.getUid()) || userAddVo.getUid() <= 0 || Tools.isNull(userAddVo) || Tools.isNull(userAddVo.getUserName()) || Tools.isNull(userAddVo.getRealName()) || Tools.isNull(userAddVo.getPwd()) || Tools.isNull(userAddVo.getPhone()) || Tools.isNull(userAddVo.getEmail())){
            logger.info("[updateUserInfo]更改用户信息，参数存在空值");
            return new ResultVo<>(-1,"请填写完整的信息");
        }
        ResultVo<Boolean> res = userService.updateUser(userAddVo);
        logger.info("[updateUserInfo]更改用户信息,end，userAddVo={},res={}", userAddVo.toString(), JSON.toJSON(res));
        return res;
    }

    /**
     * 删除用户
     * @param uid 用户id
     * @return
     */
    @RequestMapping(value = "/delete", produces="application/json;charset=UTF-8")
    public ResultVo<Boolean> delete(Long uid){
        logger.info("[delete]删除用户，uid={}", uid);
        if(Tools.isNull(uid) || uid <= 0){
            logger.info("[delete]删除用户，参数存在空值");
            return new ResultVo<>(-1,"请选择账号");
        }
        ResultVo<Boolean> res = userService.delete(uid);
        logger.info("[delete]删除用户,end,res={}", JSON.toJSON(res));
        return res;
    }


}
