package com.yidian.registration.enums;

/**
 * @Author: QingHang
 * @Description: 预约状态枚举
 * 1：预约，2：治疗，3：未治疗，4：取消，
 * @Date: 2021/3/8 23:54
 */
public enum RegistrationStateEnum {

    Registration(1, "预约"),
    cure(2, "治疗"),
    not_cure(3, "未治疗"),
    cancel(4, "取消");

    private int code;
    private String desc;

    RegistrationStateEnum(int code, String desc) {
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
    }
}
