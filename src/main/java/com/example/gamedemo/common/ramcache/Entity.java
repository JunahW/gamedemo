package com.example.gamedemo.common.ramcache;

import java.io.Serializable;

/**
 * @author: wengj
 * @date: 2019/5/24
 * @description: 实体接口
 */
public interface Entity<PK extends Serializable & Comparable<PK>> {
    /**
     * 获取元素主键
     *
     * @return
     */
    PK getId();

    /**
     * 设置缓存未命中时的id为null，guava的cache不允许返回null
     */
    void setNullId();

    /**
     * 序列化
     *
     * @return
     */
    boolean serialize();

    /**
     * 反序列化
     *
     * @return
     */
    boolean deSerialize();
}
