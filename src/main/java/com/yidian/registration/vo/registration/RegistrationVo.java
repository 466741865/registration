package com.yidian.registration.vo.registration;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: QingHang
 * @Description: TODO 注册vo
 * @Date: 2021/3/6 23:25
 */
public class RegistrationVo  implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 配置id
     */
    private Long configId;

    /**
     * 预约日期
     */
    private Date therapyDate;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 预约人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Date getTherapyDate() {
        return therapyDate;
    }

    public void setTherapyDate(Date therapyDate) {
        this.therapyDate = therapyDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "RegistrationVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", configId=" + configId +
                ", therapyDate=" + therapyDate + '\'' +
                ", state=" + state +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
