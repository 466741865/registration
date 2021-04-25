package com.yidian.registration.enums;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : 性别 枚举
 * @date : 2020/1/11 16:16
 **/
public enum SexEnum {

    MAN(1, "男"),
    WOMAN(2, "女");

    private int code;
    private String desc;

    SexEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SexEnum getEnum(int code) {
        for (SexEnum sexEnum : SexEnum.values()) {
            if (sexEnum.getCode() == code) {
                return sexEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}

