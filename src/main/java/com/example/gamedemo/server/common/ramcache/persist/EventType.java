package com.example.gamedemo.server.common.ramcache.persist;

/**
 * @author: wengj
 * @date: 2019/5/24
 * @description: 存储事件类型
 */
public enum EventType {
    /**
     * 插入
     */
    SAVE,

    /**
     * 更新
     */
    UPDATE,

    /**
     * 删除
     */
    REMOVE,

    /**
     * 查找
     */
    FIND;
}
