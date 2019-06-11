package com.example.gamedemo.common.event;

/**
 * @author: wengj
 * @date: 2019/6/11
 * @description: 事件接口
 */
public interface Event<T> {
    T getOwnerId();
}
