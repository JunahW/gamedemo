package com.example.gamedemo.common.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author wengj
 * @description:序列化和反序列化工具
 * @date 2019/5/29
 */
public class JsonUtils {


    /**
     * 序列化对象
     *
     * @param object
     * @return
     */
    public static String serializeEntity(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 反序列化对象
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deSerializeEntity(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }
}
