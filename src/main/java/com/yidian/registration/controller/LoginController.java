package com.yidian.registration.controller;

import com.yidian.registration.entity.SysUserEntity;
import com.yidian.registration.service.IUserService;
import com.yidian.registration.vo.ObjectToJsonStringUtils;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.usermanager.UserInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : TODO
 * @date : 2019/12/26 17:56
 **/
@RestController
@RequestMapping("/appraising/user")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/getInfo", produces="application/json;charset=UTF-8")
    public String getUserInfoById(Long uid){
        ResultVo<UserInfoVo> resultVo = new ResultVo<>();
        if(uid == null || uid <= 0){
            resultVo.setCode(-1);
            resultVo.setMessage("参数异常");
            return ObjectToJsonStringUtils.toStringByGson(resultVo);
        }

        SysUserEntity entity = userService.getUserInfoById(uid);
        logger.info("[getUserInfoById]查询到用户信息：res：{}",entity);
        if(entity != null){
            UserInfoVo vo = new UserInfoVo();
            vo.setUid(entity.getId());
            vo.setPhone(entity.getMobile());
            vo.setRealName(entity.getRealName());
            vo.setUserName(entity.getUsername());
            vo.setEmail(entity.getEmail());
            resultVo.setCode(1);
            resultVo.setData(vo);
        }
        return ObjectToJsonStringUtils.toStringByGson(resultVo);
    }


    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", produces="application/json;charset=UTF-8")
    public ResultVo<UserInfoVo> login(String userName,String password){
        if(userName == null || userName.trim() == "" || password == null || password.trim() == ""){
            return new ResultVo<>(-1,"参数异常");
        }
        SysUserEntity entity = userService.getUserInfoByAccount(userName,password);
        if(entity == null){
            return new ResultVo<>(-10,"用户名或密码错误");
        }
        logger.info("[login]查询到用户信息：res：{}",entity);
        UserInfoVo vo = new UserInfoVo();
        vo.setUid(entity.getId());
        vo.setRealName(entity.getRealName());
        vo.setPhone(entity.getMobile());
        vo.setUserName(entity.getUsername());
        vo.setEmail(entity.getEmail());
        return new ResultVo<>(vo);
    }



}
