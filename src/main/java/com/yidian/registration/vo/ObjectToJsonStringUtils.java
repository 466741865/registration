package com.yidian.registration.vo;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yidian.registration.utils.AesEncrypt;
import com.yidian.registration.utils.DateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ObjectToJsonStringUtils {
    public static final Logger logger = LoggerFactory.getLogger(ObjectToJsonStringUtils.class);

    public static String toJsonString(Object object) {
        return JSONObject.toJSONStringWithDateFormat(object, DateBuilder.FORMAT_FULL);
    }

    public static String toEncryptJsonString(String apiKey, Object object) {
        String result = JSONObject.toJSONStringWithDateFormat(object, DateBuilder.FORMAT_FULL);
        logger.info("返回数据未加密-----" + result);
        AesEncrypt aesEncrypt = new AesEncrypt(apiKey);
        return aesEncrypt.encrypt(result);
    }

    public static String toStringByGson(Object object) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        logger.info("jsondata:" + gson.toJson(object));
        return gson.toJson(object);
    }

    public static Map<String, Object> json2map(String str_json) {
        Map<String, Object> res = new HashMap<>();
        try {
//            str_json = str_json.replace("{", "");
//            str_json = str_json.replace("}", "");
//            String[] parmalist = str_json.split(",");
//            for (int i = 0; i < parmalist.length; i++) {
//                String[] params = parmalist[i].split(":");
//                if (params.length != 2) {
//                    throw new Exception("参数解析出错");
//                }
//                res.put(params[0].trim(), params[1].trim());
//            }

            Gson gson = new Gson();
            res = gson.fromJson(str_json, new TypeToken<Map<String, String>>() {
            }.getType());
        } catch (Exception e) {
            logger.error("json2map参数失败----{}", str_json, e);
        }
        return res;
    }
}
