package com.yidian.registration.enums;

/**
 * @Author: QingHang
 * @Description: 预约操作枚举
 * 1：预约，2：治疗，3：未治疗，4：取消，
 * @Date: 2021/3/8 23:54
 */
public enum RegistrationOperationEnum {

    BLOCK(1, "拉黑"),
    CANCEL(2, "取消"),
    SIGN_IN(3, "签到"),
    ABSENT(4, "缺席");

    private int code;
    private String desc;

    RegistrationOperationEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static RegistrationOperationEnum getOperationEnum(int code){
        for(RegistrationOperationEnum operationEnum : RegistrationOperationEnum.values()){
            if(operationEnum.getCode() == code){
                return operationEnum;
            }
        }
        return null;
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
