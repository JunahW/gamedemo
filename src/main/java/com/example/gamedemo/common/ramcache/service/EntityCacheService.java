package com.example.gamedemo.common.ramcache.service;

import com.example.gamedemo.common.ramcache.Entity;

import java.io.Serializable;

/**
 * @author: wengj
 * @date: 2019/5/27
 * @description:
 */
public interface EntityCacheService<
        PK extends Comparable<PK> & Serializable, V extends Entity<PK>> {
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

    /**
     * 加载或创建
     *
     * @param id
     * @return
     */
    V loadOrCreate(PK id, EntityBuilder<PK, V> builder);
}
