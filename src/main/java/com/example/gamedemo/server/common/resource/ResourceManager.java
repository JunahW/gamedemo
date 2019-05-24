package com.example.gamedemo.server.common.resource;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description
 * @date 2019/5/20
 */
public class ResourceManager {
    /**
     * 存放静态资源文件
     */
    public static ConcurrentHashMap<Class, ConcurrentHashMap> resourceMap = new ConcurrentHashMap<>();

    /**
     * 通过id获取静态资源的项
     *
     * @param clazz
     * @param key
     * @return
     */
    public static <K, V> V getResourceItemById(Class<V> clazz, K key) {
        return (V) resourceMap.get(clazz).get(key);
    }

    /**
     * 获取resource对应的map
     *
     * @param clazz
     * @param <V>
     * @param <K>
     * @return
     */
    public static <K, V> ConcurrentMap<K, V> getResourceMap(Class<V> clazz) {
        return resourceMap.get(clazz);
    }

    /**
     * 新增resource项
     *
     * @param clazz
     * @param key
     * @param value
     * @param <V>
     * @param <K>
     */
    public static <K, V> void putResourceItem(Class clazz, K key, V value) {
        if (resourceMap.get(clazz) == null) {
            ConcurrentHashMap<K, V> resourceItemMap = new ConcurrentHashMap<>(16);
            resourceMap.put(clazz, resourceItemMap);
        }
        resourceMap.get(clazz).put(key, value);
    }

}
