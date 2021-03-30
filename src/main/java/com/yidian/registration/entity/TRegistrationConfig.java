package com.yidian.registration.entity;

import java.io.Serializable;
import java.util.Date;

public class TRegistrationConfig implements Serializable {
    private Long id;

    private Long date;

    private Integer dayType;

    private String startTime;

    private Integer duration;

    private Integer nums;

    private Integer state;

    private String creator;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getDayType() {
        return dayType;
    }

    public void setDayType(Integer dayType) {
        this.dayType = dayType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
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
        return "TRegistrationConfig{" +
                "id=" + id +
                ", date=" + date +
                ", dayType=" + dayType +
                ", startTime='" + startTime + '\'' +
                ", duration=" + duration +
                ", nums=" + nums +
                ", state=" + state +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}