package com.yidian.registration.utils;


/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2022/6/19 23:57
 */
public class PasswordUtils {

    /**
     * 加密
     * @param salt
     * @param string
     * @return
     */
    public static String encrypt(String salt, String string) {
        AesEncrypt aesEncrypt = new AesEncrypt(salt);
        return aesEncrypt.encrypt(string);
    }

    /**
     * 解密
     * @param salt
     * @param string
     * @return
     */
    public static String decrypt(String salt, String string) {
        AesEncrypt aesEncrypt = new AesEncrypt(salt);
        return aesEncrypt.decrypt(string);
    }

}
