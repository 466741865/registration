package com.yidian.registration.entity;

import java.io.Serializable;
import java.util.Date;

public class TPatientCureEntity implements Serializable {
    private Long id;

    private Long pid;

    private Long cureDate;

    private String cureTime;

    private String items;

    private Integer useNum;

    private Integer remainNum;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getCureDate() {
        return cureDate;
    }

    public void setCureDate(Long cureDate) {
        this.cureDate = cureDate;
    }

    public String getCureTime() {
        return cureTime;
    }

    public void setCureTime(String cureTime) {
        this.cureTime = cureTime == null ? null : cureTime.trim();
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items == null ? null : items.trim();
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pid=").append(pid);
        sb.append(", cureDate=").append(cureDate);
        sb.append(", cureTime=").append(cureTime);
        sb.append(", items=").append(items);
        sb.append(", useNum=").append(useNum);
        sb.append(", remainNum=").append(remainNum);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}