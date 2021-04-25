package com.yidian.registration.enums;

/**
 * @Author: QingHang
 * @Description: 预约状态枚举
 * @Date: 2021/3/8 23:54
 */
public enum PatientCureStateEnum {

    REGISTRATION(0, "预约"),
    CURE(1, "治疗"),
    CANCEL(2, "取消"),
    NOT_COME(3, "缺席");

    private Integer code;
    private String desc;

    PatientCureStateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
