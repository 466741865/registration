package com.yidian.registration.enums;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : 用户提成类型 枚举
 * @date : 2020/1/11 16:16
 **/
public enum UserCommissionTypeEnum {

    MAIN(1, "主"),
    DEPUTY(2, "副");

    private Integer type;
    private String desc;

    UserCommissionTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}

