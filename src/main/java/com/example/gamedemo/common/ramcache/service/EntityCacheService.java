package com.example.gamedemo.common.ramcache.service;


/**
 * @author: wengj
 * @date: 2019/5/27
 * @description:
 */
public interface EntityCacheService<PK, V> {
    /**
     * 通过id加载实体
     *
     * @param id
     * @return
     */
    V load(PK id);


    /**
     * 先缓存中的数据写入数据库
     *
     * @param id
     * @param entity
     */
    void writeBack(PK id, V entity);
}
