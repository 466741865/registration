package com.yidian.registration.vo;


import com.alibaba.fastjson.JSON;
import com.yidian.registration.enums.StatusEnum;

import java.io.Serializable;

public class PageVo<T> implements Serializable {


    private int pageNum;

    private int pageSize;

    private int code;

    private String message;

    private long count;

    private T data;

    public PageVo() {
    }

    public PageVo(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public PageVo(int code, String message, long count, T data) {
        this.code = code;
        this.message = message;
        this.count = count;
        this.data = data;
    }

    public PageVo(int code, String message, long count, T data, int pageSize, int pageNum) {
        this.code = code;
        this.message = message;
        this.count = count;
        this.data = data;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public static <T> JsonActionResult getSuccessJson(T data) {
        return new JsonActionResult(JSON.toJSON(new PageVo(StatusEnum.SUCCESS.getCode(), StatusEnum.SUCCESS.getDesc(), data)).toString());
    }

    public static <T> JsonActionResult getSuccessJson(T data, long count) {
        return new JsonActionResult(JSON.toJSON(new PageVo(StatusEnum.SUCCESS.getCode(), StatusEnum.SUCCESS.getDesc(), count, data)).toString());
    }

    public static <T> JsonActionResult getSuccessJson(T data, long count, int pageSize, int pageNum) {
        return new JsonActionResult(JSON.toJSON(new PageVo(StatusEnum.SUCCESS.getCode(), StatusEnum.SUCCESS.getDesc(), count, data, pageSize, pageNum)).toString());
    }

    public static JsonActionResult getFailJson() {
        return new JsonActionResult(JSON.toJSON(new PageVo(StatusEnum.FAIL_CODE.getCode(), StatusEnum.FAIL_CODE.getDesc(), null)).toString());
    }

    public static JsonActionResult getFailJson(String msg) {
        return new JsonActionResult(JSON.toJSON(new PageVo(StatusEnum.FAIL_CODE.getCode(),msg, null)).toString());
    }

    public static JsonActionResult getNoDataJson() {
        return new JsonActionResult(JSON.toJSON(new PageVo(StatusEnum.INVALID_PARAM_CODE.getCode(), StatusEnum.INVALID_PARAM_CODE.getDesc(), null)).toString());
    }


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
