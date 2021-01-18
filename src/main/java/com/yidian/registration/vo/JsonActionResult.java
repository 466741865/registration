package com.yidian.registration.vo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonActionResult {
    /**
     * json 标准格式
     */
    public static final String CONTENT_TYPE_APPLICATION = "application/json";
    /**
     * 兼容之前的格式
     */
    public static final String CONTENT_TYPE_TEXT = "text/json";
    private static Logger logger = LoggerFactory.getLogger(JsonActionResult.class);
    private String text = null;
    private String encoding = "UTF-8";
    private String contentType = "text/json";

    public JsonActionResult(String text) {
        this.text = text;
    }

    public JsonActionResult(String text, String contentType) {
        this.text = text;
        this.contentType = contentType;
    }
    public static <T> JsonActionResult getJsonActionResult(T data) {
        return new JsonActionResult(JSON.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
    }

}
