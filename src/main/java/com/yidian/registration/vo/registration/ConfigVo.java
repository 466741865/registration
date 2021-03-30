package com.yidian.registration.vo.registration;

import java.util.Date;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/1/21 23:32
 */
public class ConfigVo {


    private Long id;

    /**
     * 日期
     */
    private Date date;

    /**
     * 上下午，1：上午，2：下午
     */
    private int type;

    /**
     * 单个时长
     */
    private int duration;

    /**
     * 号源数
     */
    private int nums;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 创建人
     */
    private String creator;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
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
        return "ConfigVo{" +
                "id=" + id +
                ", date=" + date +
                ", type=" + type +
                ", duration=" + duration +
                ", nums=" + nums +
                ", startTime=" + startTime +
                ", creator=" + creator +
                '}';
    }
}
