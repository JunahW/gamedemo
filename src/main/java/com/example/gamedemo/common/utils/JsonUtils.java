package com.example.gamedemo.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
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

  /**
   * 反序列化Map
   *
   * @param mapClass
   * @param keyClass
   * @param valueClass
   * @param value
   * @param <M>
   * @param <K>
   * @param <V>
   * @return
   */
  public static <M, K, V> M deSerializeMap(
      Class<M> mapClass, Class<K> keyClass, Class<V> valueClass, String value) {
    JavaType javaType =
        mapper.getTypeFactory().constructParametricType(mapClass, keyClass, valueClass);
    M m = null;
    try {
      m = (M) mapper.readValue(value, javaType);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return m;
  }

  /**
   * 反序列化set
   *
   * @param setClass
   * @param valueClass
   * @param value
   * @param <S>
   * @param <V>
   * @return
   */
  public static <S, V> S deSerializeSet(Class<S> setClass, Class<V> valueClass, String value) {
    JavaType javaType = mapper.getTypeFactory().constructParametricType(setClass, valueClass);
    S s = null;
    try {
      s = (S) mapper.readValue(value, javaType);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return s;
  }
}
