package com.example.gamedemo.common.ramcache;

import java.io.Serializable;

/**
 * @author wengj
 * @description: 缓存没命中时返回的实体
 * @date 2019/5/29
 */
public class EmptyEntity<PK extends Serializable & Comparable<PK>> implements Entity<PK> {
    @Override
    public PK getId() {
        return null;
    }

    @Override
    public void setNullId() {

    }

    @Override
    public boolean serialize() {
        return false;
    }

    @Override
    public boolean deSerialize() {
        return false;
    }
}
