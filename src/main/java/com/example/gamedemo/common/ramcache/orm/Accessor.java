package com.example.gamedemo.common.ramcache.orm;

import java.io.Serializable;

/**
 * @author: wengj
 * @date: 2019/5/24
 * @description: 数据库访问接口，用于给不同orm提供实现
 */
public interface Accessor {
    /**
     * 加载对象
     *
     * @param clazz
     * @param id
     * @param <PK>
     * @param <T>
     * @return
     */
    <PK extends Serializable, T> T load(Class<T> clazz, PK id);

    /**
     * 保存对象
     *
     * @param clazz
     * @param entity
     * @param <PK>
     * @param <T>
     * @return
     */
    <PK extends Serializable, T> PK save(Class<T> clazz, T entity);

    /**
     * 移除对象
     *
     * @param clazz
     * @param id
     * @param <PK>
     * @param <T>
     * @return
     */
    <PK extends Serializable, T> void remove(Class<T> clazz, PK id);

    /**
     * 更新
     *
     * @param clazz
     * @param entity
     * @param <PK>
     * @param <T>
     * @return
     */
    <PK extends Serializable, T> void saveOrUpdate(Class<T> clazz, T entity);
}