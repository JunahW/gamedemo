package com.example.gamedemo.common.ramcache.service;

import com.example.gamedemo.common.ramcache.EmptyEntity;
import com.google.common.cache.CacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengj
 * @description 从数据库加载实体
 * @date 2019/5/23
 */
public class EntityCacheLoader<PK, V> extends CacheLoader<PK, V> {
    private static final Logger logger = LoggerFactory.getLogger(EntityCacheLoader.class);


    @Override
    public V load(PK k) throws Exception {
        logger.info("缓存中没有命中");

        return (V) new EmptyEntity<String>();
    }


}
