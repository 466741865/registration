package com.yidian.registration.vo;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : TODO
 * @date : 2019/7/8 17:03
 **/
public class ResultVo<T> {

    public static final int SUCCESS_CODE = 1;
    public static final String SUCCESS_MESSAGE = "success";


    private int code;

    private String message;

    private T data;


    public ResultVo() {
    }

    public ResultVo(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }



    public ResultVo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultVo(T data) {
        this.data = data;
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MESSAGE;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
