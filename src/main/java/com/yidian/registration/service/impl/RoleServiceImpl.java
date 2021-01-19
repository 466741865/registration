package com.yidian.registration.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yidian.registration.dao.SysRoleDao;
import com.yidian.registration.entity.SysRoleEntity;
import com.yidian.registration.enums.StatusEnum;
import com.yidian.registration.service.IRoleService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.rolemanager.RoleInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author QingHang
 * @Description : TODO
 * @Date 2020/3/7 23:40
 * @Version 1.0
 */
@Service
public class RoleServiceImpl implements IRoleService {


    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private SysRoleDao roleDao;

    @Override
    public SysRoleEntity getRoleInfoById(Long rid) {
        SysRoleEntity entity = roleDao.selectByPrimaryKey(rid);
        return entity;
    }

    @Override
    public PageVo<List<RoleInfoVo>> getRoleList(String roleName, Integer pageNo, Integer pageSize) {

        List<RoleInfoVo> vos = new ArrayList<>();
        PageVo<List<RoleInfoVo>> pageVo = new PageVo<>();
        //查询用户列表
        try {
            //查询数据量
            int count = roleDao.getRoleListCount(roleName);
            logger.info("[getRoleList]询角色列表,查询到角色数量为：{}，roleName={}", count, roleName);
            if (count <= 0) {
                pageVo.setCode(StatusEnum.SUCCESS.getCode());
                pageVo.setData(vos);
                pageVo.setCount(0);
                return pageVo;
            }
            pageVo.setCount(count);
            //查询列表
            List<SysRoleEntity> list = roleDao.getRoleList(roleName, (pageNo - 1) * pageSize, pageSize);
            logger.info("[getRoleList]询角色列表,查询到角色列表，roleName={},pageNo={},pageSize={}，list：{}", roleName, pageNo, pageSize, JSONObject.toJSON(list));
            if (Tools.isNull(list)) {
                pageVo.setCode(StatusEnum.SUCCESS.getCode());
                pageVo.setData(vos);
                return pageVo;
            }
            for (SysRoleEntity entity : list) {
                RoleInfoVo vo = new RoleInfoVo();
                vo.setRoleName(entity.getRoleName());
                vo.setRemark(entity.getRemark());
                vo.setRid(entity.getId());
                vo.setCreateTime(entity.getCreateTime());

                vos.add(vo);
            }
            pageVo.setCode(StatusEnum.SUCCESS.getCode());
            pageVo.setData(vos);
            return pageVo;
        } catch (Exception e) {
            logger.error("[getRoleList]根据条件查询角色列表出现异常", e);
        }
        pageVo.setCode(StatusEnum.FAIL_CODE.getCode());
        pageVo.setMessage(StatusEnum.FAIL_CODE.getDesc());
        return pageVo;
    }

    @Override
    public ResultVo<Boolean> addRole(String roleName, String remark) {
        ResultVo<Boolean> resultVo = new ResultVo<>();
        try{
            SysRoleEntity entity = new SysRoleEntity();
            entity.setRoleName(roleName.trim());
            entity.setRemark(remark.trim());
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());

            int res = roleDao.insert(entity);
            logger.info("[addRole]添加角色完成，，result：{}",res);
            if(res > 0){
                resultVo.setCode(StatusEnum.SUCCESS.getCode());
                resultVo.setData(true);
                return resultVo;
            }

        }catch (Exception e){
            logger.error("[addRole]添加角色出现异常，",e);
        }
        resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
        resultVo.setMessage("角色添加失败");
        resultVo.setData(false);
        return resultVo;
    }

    @Override
    public ResultVo<Boolean> updateRole(long rid, String roleName, String remark) {
        ResultVo<Boolean> resultVo = new ResultVo<>();
        try{
            SysRoleEntity entity = roleDao.selectByPrimaryKey(rid);
            if(Tools.isNull(entity)){
                resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
                resultVo.setMessage("角色不存在");
                return resultVo;
            }
            entity.setRoleName(roleName.trim());
            entity.setRemark(remark.trim());
            entity.setUpdateTime(new Date());

            int res = roleDao.updateByPrimaryKey(entity);
            logger.info("[updateRole]编辑角色信息完成，，result：{}",res);
            if(res > 0){
                resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
                resultVo.setMessage("修改成功");
                resultVo.setData(true);
                return resultVo;
            }
        }catch (Exception e){
            logger.error("[updateRole]编辑角色信息出现异常，",e);
        }
        resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
        resultVo.setMessage("修改失败");
        resultVo.setData(false);
        return resultVo;
    }

    @Override
    public ResultVo<Boolean> deleteRole(Long rid) {
        ResultVo<Boolean> resultVo = new ResultVo<>();
        try{
            SysRoleEntity entity = roleDao.selectByPrimaryKey(rid);
            if(Tools.isNull(entity)){
                resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
                resultVo.setMessage("用户不存在");
                return resultVo;
            }
            int res = roleDao.deleteByPrimaryKey(rid);
            if(res > 0){
                resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
                resultVo.setMessage("删除成功");
                resultVo.setData(true);
                return resultVo;
            }
        }catch (Exception e){
            logger.error("[updateUserState]编辑用户状态出现异常，",e);
        }
        resultVo.setCode(StatusEnum.FAIL_CODE.getCode());
        resultVo.setMessage("删除失败");
        resultVo.setData(false);
        return resultVo;
    }
}
