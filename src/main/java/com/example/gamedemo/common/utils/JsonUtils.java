package com.example.gamedemo.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.List;

/**
 * @author wengj
 * @description:序列化和反序列化工具
 * @date 2019/5/29
 */
public class JsonUtils {

  private static final ObjectMapper mapper =
      new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

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

  /**
   * 将json字符串装换为list集合
   *
   * @param jsonString
   * @param typeReference
   * @return
   */
  public static <T> List<T> getListByString(
      String jsonString, TypeReference<List<T>> typeReference) {
    List<T> list = null;
    try {
      list = mapper.readValue(jsonString, typeReference);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
  }
}
