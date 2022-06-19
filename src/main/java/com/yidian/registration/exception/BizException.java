package com.yidian.registration.exception;

import com.yidian.registration.enums.StatusEnum;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2022/6/19 13:23
 */
public class BizException extends RuntimeException{

    private Integer code;

    public BizException(Integer code, String message){
        this(code,message, (Throwable)null);
    }


    public BizException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BizException(StatusEnum statusEnum){
        super(statusEnum.getDesc());
        this.code = statusEnum.getCode();
    }

}
