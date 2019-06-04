package com.example.gamedemo.common.resource;

/**
 * @author wengj
 * @description
 * @date 2019/5/20
 */
public interface ResourceInterface {

    /**
     * 获取静态资源的主键
     *
     * @return
     */
    Object getId();

    /**
     * 加载完处理数据
     */
    void postInit();
}
