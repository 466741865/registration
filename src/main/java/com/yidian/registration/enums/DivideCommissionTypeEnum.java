package com.yidian.registration.enums;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : 项目分成提成类型 枚举
 * @date : 2020/1/11 16:16
 **/
public enum DivideCommissionTypeEnum {

    MAIN(1, "开票提成"),
    DEPUTY(2, "抽成提成");

    private Integer type;
    private String desc;

    DivideCommissionTypeEnum(Integer type, String desc) {
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

