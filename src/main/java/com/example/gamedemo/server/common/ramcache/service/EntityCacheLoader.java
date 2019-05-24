package com.example.gamedemo.server.common.ramcache.service;

import com.google.common.cache.CacheLoader;

/**
 * @author wengj
 * @description 从数据库加载实体
 * @date 2019/5/23
 */
public class EntityCacheLoader<K, V> extends CacheLoader<K, V> {

    private Class<V> clazz;

    public EntityCacheLoader() {
    }

    public EntityCacheLoader(Class<V> clazz) {
        this.clazz = clazz;
    }

    public Class<V> getClazz() {
        return clazz;
    }

    public void setClazz(Class<V> clazz) {
        this.clazz = clazz;
    }

    @Override
    public V load(K k) throws Exception {
        System.out.println(clazz);

        return null;
    }


}
