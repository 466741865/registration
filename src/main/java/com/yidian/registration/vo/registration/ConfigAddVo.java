package com.yidian.registration.vo.registration;

import java.util.Date;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/1/21 23:32
 */
public class ConfigAddVo {

    /**
     * 日期
     */
    private Date date;

    /**
     * 上下午，1：上午，2：下午
     */
    private Integer type;

    /**
     * 单个时长
     */
    private Integer duration;

    /**
     * 号源数
     */
    private Integer nums;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 创建人
     */
    private String creator;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "ConfigAddVo{" +
                "date=" + date +
                ", type=" + type +
                ", duration=" + duration +
                ", nums=" + nums +
                ", startTime=" + startTime +
                ", creator='" + creator + '\'' +
                '}';
    }
}
