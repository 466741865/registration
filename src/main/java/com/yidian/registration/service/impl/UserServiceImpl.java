package com.yidian.registration.service.impl;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.dao.SysUserDao;
import com.yidian.registration.entity.SysUserEntity;
import com.yidian.registration.enums.StatusEnum;
import com.yidian.registration.enums.UserStatusEnum;
import com.yidian.registration.service.IUserService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.usermanager.UserAddVo;
import com.yidian.registration.vo.usermanager.UserInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author : QingHang
 * @version 1.0
 * @Description : TODO
 * @date : 2019/12/26 17:41
 **/
@Service
public class UserServiceImpl implements IUserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private SysUserDao userDao;

    @Override
    public SysUserEntity getUserInfoById(Long uid) {
        SysUserEntity entity = userDao.selectByPrimaryKey(uid);
        return entity;
    }

    @Override
    public SysUserEntity getUserInfoByAccount(String userName, String password) {
        SysUserEntity entity = userDao.selectByUserNameAndPwd(userName, password);
        return entity;
    }

    @Override
    public SysUserEntity getUserInfoByName(String userName) {
        SysUserEntity entity = userDao.selectByUserName(userName);
        return entity;
    }

    @Override
    public PageVo<List<UserInfoVo>> getUserList(String mobile, String realName, Integer state, Integer pageNo, Integer pageSize) {


        List<UserInfoVo> vos = new ArrayList<>();
        PageVo<List<UserInfoVo>> pageVo = new PageVo<>();
        //查询用户列表
        try {

            //查询数据量
            int count = userDao.getUserListCount(mobile, realName, state);
            logger.info("[getUserList]根据条件查询用户列表,查询到用户数量为：{}，mobile={},realName={},state={}", count, mobile, realName, state);
            if (count <= 0) {
                pageVo.setCode(StatusEnum.SUCCESS.getCode());
                pageVo.setData(vos);
                pageVo.setCount(0);
                return pageVo;
            }
            pageVo.setCount(count);
            //查询列表
            List<SysUserEntity> list = userDao.getUserList(mobile, realName, state, (pageNo - 1) * pageSize, pageSize);
            logger.info("[getUserList]根据条件查询用户列表,查询到用户列表，mobile={},realName={},state={}，list：{}", mobile, realName, state, JSON.toJSON(list));
            if (Tools.isNull(list)) {
                pageVo.setCode(StatusEnum.SUCCESS.getCode());
                pageVo.setData(vos);
                return pageVo;
            }
            for (SysUserEntity entity : list) {
                UserInfoVo vo = new UserInfoVo();
                vo.setUserName(entity.getUsername());
                vo.setRealName(entity.getRealName());
                vo.setEmail(entity.getEmail());
                vo.setPhone(entity.getMobile());
                vo.setState(entity.getState());
                vo.setUid(entity.getId());
                vo.setCreateTime(entity.getCreateTime());

                vos.add(vo);
            }
            pageVo.setCode(StatusEnum.SUCCESS.getCode());
            pageVo.setData(vos);
            return pageVo;
        } catch (Exception e) {
            logger.error("[getUserList]根据条件查询用户列表出现异常", e);
        }
        pageVo.setCode(StatusEnum.FAIL_CODE.getCode());
        pageVo.setMessage(StatusEnum.FAIL_CODE.getDesc());
        return pageVo;
    }

    @Override
    public ResultVo<Boolean> addUser(UserAddVo userAddVo) {
        logger.info("[addUser]添加用户--start--，userAddVo：{}",userAddVo.toString());
        ResultVo<Boolean> resultVo = new ResultVo<>();
        try{
            SysUserEntity entity = new SysUserEntity();
            entity.setMobile(userAddVo.getPhone().trim());
            entity.setPassword(userAddVo.getPwd().trim());
            entity.setRealName(userAddVo.getRealName().trim());
            entity.setEmail(userAddVo.getEmail().trim());
            entity.setUsername(userAddVo.getUserName().trim());
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            entity.setState((byte) UserStatusEnum.ENABLED.getCode());

            int res = userDao.insert(entity);
            logger.info("[addUser]添加用户完成，--end--，result：{}",res);
            if(res > 0){
                resultVo.setCode(StatusEnum.SUCCESS.getCode());
                resultVo.setData(true);
                return resultVo;
            }

        }catch (Exception e){
            logger.error("[addUser]添加用户出现异常，",e);
        }
        resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
        resultVo.setMessage("用户添加失败");
        resultVo.setData(false);
        return resultVo;
    }

    @Override
    public ResultVo<Boolean> updateUser(UserAddVo userAddVo) {
        logger.info("[updateUser]编辑用户信息--start--，userAddVo：{}",userAddVo.toString());
        ResultVo<Boolean> resultVo = new ResultVo<>();
        try{
            SysUserEntity entity = userDao.selectByPrimaryKey(userAddVo.getUid());
            if(Tools.isNull(entity)){
                resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
                resultVo.setMessage("用户不存在");
                return resultVo;
            }
            entity.setMobile(userAddVo.getPhone().trim());
            entity.setPassword(userAddVo.getPwd().trim());
            entity.setRealName(userAddVo.getRealName().trim());
            entity.setEmail(userAddVo.getEmail().trim());
            entity.setUsername(userAddVo.getUserName().trim());
            entity.setUpdateTime(new Date());

            int res = userDao.updateByPrimaryKey(entity);
            logger.info("[updateUser]编辑用户信息完成，--end--，result：{}",res);
            if(res > 0){
                resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
                resultVo.setMessage("修改成功");
                resultVo.setData(true);
                return resultVo;
            }
        }catch (Exception e){
            logger.error("[updateUser]编辑用户信息出现异常，",e);
        }
        resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
        resultVo.setMessage("修改失败");
        resultVo.setData(false);
        return resultVo;
    }

    @Override
    public ResultVo<Boolean> updateUserState(Long uid) {
        logger.info("[updateUserState]编辑用户状态--start--，uid：{}",uid);
        ResultVo<Boolean> resultVo = new ResultVo<>();
        String message = "停用";
        try{
            SysUserEntity entity = userDao.selectByPrimaryKey(uid);
            if(Tools.isNull(entity)){
                resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
                resultVo.setMessage("用户不存在");
                return resultVo;
            }
            if(entity.getState() == UserStatusEnum.DISABLE.getCode()){
                entity.setState((byte) UserStatusEnum.ENABLED.getCode());
                message = UserStatusEnum.ENABLED.getDesc();
            }else if(entity.getState() == UserStatusEnum.ENABLED.getCode()){
                entity.setState((byte) UserStatusEnum.DISABLE.getCode());
                message = UserStatusEnum.DISABLE.getDesc();
            }
            int res = userDao.updateByPrimaryKey(entity);
            logger.info("[updateUserState]编辑用户状态完成，--end--，result：{}",res);
            if(res > 0){
                resultVo.setCode(StatusEnum.SUCCESS.getCode());
                resultVo.setMessage(message+"成功");
                resultVo.setData(true);
                return resultVo;
            }
        }catch (Exception e){
            logger.error("[updateUserState]编辑用户状态出现异常，",e);
        }
        resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
        resultVo.setMessage(message+"失败");
        resultVo.setData(false);
        return resultVo;
    }

    @Override
    public ResultVo<Boolean> delete(Long uid) {
        logger.info("[updateUserState]编辑用户状态--start--，uid：{}",uid);
        ResultVo<Boolean> resultVo = new ResultVo<>();
        String message = "停用";
        try{
            SysUserEntity entity = userDao.selectByPrimaryKey(uid);
            if(Tools.isNull(entity)){
                resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
                resultVo.setMessage("用户不存在");
                return resultVo;
            }
            int res = userDao.deleteByPrimaryKey(uid);
            if(res > 0){
                resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
                resultVo.setMessage(message+"成功");
                resultVo.setData(true);
                return resultVo;
            }
        }catch (Exception e){
            logger.error("[updateUserState]编辑用户状态出现异常，",e);
        }
        resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
        resultVo.setMessage(message+"失败");
        resultVo.setData(false);
        return resultVo;
    }
}
