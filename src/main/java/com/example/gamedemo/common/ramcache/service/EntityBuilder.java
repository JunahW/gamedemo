package com.example.gamedemo.common.ramcache.service;

import com.example.gamedemo.common.ramcache.Entity;

import java.io.Serializable;

/**
 * Entity构建器
 *
 * @param <PK>
 * @param <T>
 */
public interface EntityBuilder<PK extends Comparable<PK> & Serializable, T extends Entity> {
    T newInstance(PK id);
}
