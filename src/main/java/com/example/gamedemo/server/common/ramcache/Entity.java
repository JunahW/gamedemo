package com.example.gamedemo.server.common.ramcache;

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
}
