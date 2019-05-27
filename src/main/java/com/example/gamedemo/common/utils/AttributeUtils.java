package com.example.gamedemo.common.utils;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * @author: wengj
 * @date: 2019/5/6
 * @description: 属性操作工具
 */
public class AttributeUtils {

    /**
     * 获取绑定到channel上面的数据
     *
     * @param channel
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T get(Channel channel, AttributeKey<T> key) {
        return channel.attr(key).get();
    }

    /**
     * 绑定某个数据到channel
     *
     * @param channel
     * @param key
     * @param value
     * @param <T>
     */
    public static <T> void set(Channel channel, AttributeKey<T> key, T value) {
        channel.attr(key).set(value);
    }

    private AttributeUtils() {
    }
}
