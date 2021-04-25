package com.yidian.registration.enums;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : 用户状态码 枚举 状态,1:正常，2：欠费，3：禁用
 * @date : 2020/1/11 16:16
 **/
public enum PatientUserStatusEnum {

    NORMAL(1, "正常"),
    ARREARAGE(2, "欠费"),
    DISABLE(3, "禁用");

    private int code;
    private String desc;

    PatientUserStatusEnum(int code, String desc) {
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
