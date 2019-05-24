package com.example.gamedemo.server.common.ramcache.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author wengj
 * @description 实体缓存服务
 * @date 2019/5/23
 */
public class EntityCacheServiceImpl<K, V> implements EntityCacheService<K, V> {

    private Class<V> clazz;


    private LoadingCache<K, V> cache = CacheBuilder.newBuilder()
            //设置并发级别为8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            //设置缓存容器的初始容量为10
            .initialCapacity(10)
            //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(100)
            //是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
            .recordStats()
            //设置写缓存后n秒钟过期
            .expireAfterWrite(600, TimeUnit.SECONDS)
            //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
            //.expireAfterAccess(17, TimeUnit.SECONDS)
            //只阻塞当前数据加载线程，其他线程返回旧值
            //.refreshAfterWrite(13, TimeUnit.SECONDS)
            //设置缓存的移除通知
            .removalListener(notification -> {
                System.out.println(notification.getKey() + " " + notification.getValue() + " 被移除,原因:" + notification.getCause());
            })
            //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
            .build(new EntityCacheLoader<>(clazz));

    public EntityCacheServiceImpl(Class<V> clazz) {
        this.clazz = clazz;
    }

    public Class<V> getClazz() {
        return clazz;
    }

    public LoadingCache<K, V> getCache() {
        return cache;
    }


    public void setClazz(Class<V> clazz) {
        this.clazz = clazz;
    }

    public void setCache(LoadingCache<K, V> cache) {
        this.cache = cache;
    }

    @Override
    public V load(K id) {
        V entity = null;
        try {
            entity = cache.get(id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void writeBack(K id, V entity) {

    }
}