package com.yidian.registration.vo.req;

import java.util.Date;

/**
 * @Author: QingHang
 * @Description: 预约列表查询请求实体
 * @Date: 2021/3/8 23:25
 */
public class RegistrationListReq {

    private String name;

    private String mobile;

    private Date day;

    private Integer page;

    private Integer limit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "RegistrationListReq{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", day=" + day +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
