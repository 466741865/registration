package com.yidian.registration.enums;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : z状态码 枚举
 * @date : 2020/1/11 16:16
 **/
public enum StatusEnum {

    SUCCESS(0, "成功"),
    FAIL_CODE(-1, "查询失败"),
    NODATA_CODE(-2, "参数不合法"),
    INVALID_PARAM_CODE(-3, "数据不存在"),

    NOT_USER_INFO(1001, "用户信息不存在"),

    OPERATE_TYPE_FAIL(2001, "请选择正确的操作类型"),
    CURE_DATA_FAIL(2002, "请选择正确的预约记录"),
    CURE_DATA_SGIN_IN(2003, "该预约无法改变")

    ;



    private int code;
    private String desc;

    StatusEnum(int code, String desc) {
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
