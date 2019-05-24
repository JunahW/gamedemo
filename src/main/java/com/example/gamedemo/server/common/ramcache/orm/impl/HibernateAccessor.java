package com.example.gamedemo.server.common.ramcache.orm.impl;

import com.example.gamedemo.server.common.ramcache.orm.Accessor;

import java.io.Serializable;

/**
 * @author wengj
 * @description
 * @date 2019/5/24
 */
public class HibernateAccessor implements Accessor {

    @Override
    public <PK extends Serializable, T> T load(Class<T> clazz, PK id) {
        return null;
    }

    @Override
    public <PK extends Serializable, T> T save(Class<T> clazz, T entity) {
        return null;
    }

    @Override
    public <PK extends Serializable, T> T remove(Class<T> clazz, PK id) {
        return null;
    }

    @Override
    public <PK extends Serializable, T> T saveOrUpdate(Class<T> clazz, T entity) {
        return null;
    }
}
