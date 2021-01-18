package com.yidian.registration.enums;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : 用户状态码 枚举
 * @date : 2020/1/11 16:16
 **/
public enum UserStatusEnum {

    DISABLE(0, "禁用"),
    ENABLED(1, "启用");

    private int code;
    private String desc;

    UserStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}
