package com.example.gamedemo.server.common.ramcache.service;

/**
 * @param <K> 实体主键类型
 * @param <V> 实体类型
 */
public interface EntityCacheService<K, V> {
    /**
     * 通过id加载实体
     *
     * @param id
     * @return
     */
    V load(K id);


    /**
     * 先缓存中的数据写入数据库
     *
     * @param id
     * @param entity
     */
    void writeBack(K id, V entity);
}
