package com.example.gamedemo.common.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author wengj
 * @description:序列化和反序列化工具
 * @date 2019/5/29
 */
public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 序列化对象
     *
     * @param object
     * @return
     */
    public static String serializeEntity(Object object) {
        String value = null;
        try {
            value = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return value;
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
        T readValue = null;
        try {
            readValue = mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readValue;
    }
}
