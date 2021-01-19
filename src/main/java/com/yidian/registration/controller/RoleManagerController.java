package com.yidian.registration.controller;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.service.IRoleService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.rolemanager.RoleInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : 角色管理
 * @date : 2020/1/11 15:27
 **/
@RestController
@RequestMapping("/role/manager")
public class RoleManagerController {


    Logger logger = LoggerFactory.getLogger(RoleManagerController.class);

    @Autowired
    private IRoleService roleService;

    /**
     * 获取角色列表
     * @param roleName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getRoleList", produces="application/json;charset=UTF-8")
    public PageVo<List<RoleInfoVo>> getRoleList(String roleName, Integer pageNo, Integer pageSize){
        logger.info("[getRoleList]获取角色列表表,start，roleName={},pageNo={},pageSize={}", roleName , pageNo, pageSize);
        if(Tools.isNull(pageNo) || pageNo <= 0 ){
            pageNo = 1;
        }if(Tools.isNull(pageSize) || pageSize <= 0){
            pageSize = 10;
        }
        PageVo<List<RoleInfoVo>> pageVos = roleService.getRoleList(roleName,pageNo,pageSize);
        pageVos.setPageNum(pageNo);
        pageVos.setPageSize(pageSize);
        logger.info("[getRoleList]获取角色列表,end，roleName={},pageNo={},pageSize={},res:{}", roleName , pageNo, pageSize,JSON.toJSON(pageVos));
        return pageVos;

    }

    /**
     * 添加角色
     * @return
     */
    @RequestMapping(value = "/addRole", produces="application/json;charset=UTF-8")
    public ResultVo<Boolean> addRole(String roleName , String remark){
        logger.info("[addRole]添加角色，roleName={}，remark={}", roleName,remark);
        if(Tools.isNull(roleName) || Tools.isNull(remark) ){
            logger.info("[addRole]添加角色，参数存在空值");
            return new ResultVo<>(-1,"请填写完整的信息");
        }

        ResultVo<Boolean> res = roleService.addRole(roleName,remark);
        logger.info("[addRole]添加角色,end，roleName={},remark={},res:{}",roleName,remark,JSON.toJSON(res));
        return res;
    }

    /**
     * 更改角色信息
     * @return
     */
    @RequestMapping(value = "/updateRoleInfo", produces="application/json;charset=UTF-8")
    public ResultVo<Boolean> updateRoleInfo(Long rid ,String roleName , String remark){
        logger.info("[updateRoleInfo]更改角色信息，rid={},roleName={},remark={}", rid,roleName,remark);
        if(Tools.isNull(rid) || rid <= 0 || Tools.isNull(roleName) || Tools.isNull(roleName) || Tools.isNull(remark)){
            logger.info("[updateRoleInfo]更改角色信息，参数存在空值");
            return new ResultVo<>(-1,"请填写完整的信息");
        }
        ResultVo<Boolean> res = roleService.updateRole(rid,roleName,remark);
        logger.info("[updateRoleInfo]更改角色信息,end，rid={},roleName={},remark={},res:{}", rid,roleName,remark,JSON.toJSON(res));
        return res;
    }

    /**
     * 删除角色
     * @param rid 角色id
     * @return
     */
    @RequestMapping(value = "/delete", produces="application/json;charset=UTF-8")
    public ResultVo<Boolean> delete(Long rid){
        logger.info("[delete]删除角色，rid={}", rid);
        if(Tools.isNull(rid) || rid <= 0){
            logger.info("[delete]删除角色，参数存在空值");
            return new ResultVo<>(-1,"请选择账号");
        }
        ResultVo<Boolean> res = roleService.deleteRole(rid);
        logger.info("[delete]删除角色,end,res={}", JSON.toJSON(res));
        return res;
    }


}
