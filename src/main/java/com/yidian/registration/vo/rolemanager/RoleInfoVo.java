package com.yidian.registration.vo.rolemanager;

import java.util.Date;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : 角色信息info
 * @date : 2019/12/26 17:58
 **/
public class RoleInfoVo {


    private Long rid;

    private String roleName;

    private String remark;

    private Date createTime;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RoleInfoVo{" +
                "rid=" + rid +
                ", roleName='" + roleName + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
