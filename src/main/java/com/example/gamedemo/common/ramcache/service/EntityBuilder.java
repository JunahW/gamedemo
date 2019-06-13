package com.example.gamedemo.common.ramcache.service;

import com.example.gamedemo.common.ramcache.Entity;

import java.io.Serializable;

/**
 * @author: wengj
 * @date: 2019/6/4
 * @description: Entity构建器
 */
public interface EntityBuilder<PK extends Comparable<PK> & Serializable, T extends Entity> {
  /**
   * 创建实例
   *
   * @param id
   * @return
   */
  T newInstance(PK id);
}
