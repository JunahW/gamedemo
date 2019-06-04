package com.example.gamedemo.common.ramcache.service;

import com.example.gamedemo.common.ramcache.EmptyEntity;
import com.example.gamedemo.common.ramcache.Entity;
import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.persist.QueueConsumer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author wengj
 * @description 实体缓存服务
 * @date 2019/5/23
 */
@Component
public class EntityCacheServiceImpl<PK extends Comparable<PK> & Serializable, V extends Entity<PK>> implements EntityCacheService<PK, V> {

    /**
     * 实体类型
     */
    private Class<V> clazz;

    /**
     * 存储器
     */

    @Autowired
    private Accessor accessor;

    /**
     * 持久化缓存器
     */
    //private Persister persister;

//TODO accessor初始化为空
    /**
     * 更新队列
     */
    private QueueConsumer consumer = new QueueConsumer(new LinkedBlockingDeque<>(), accessor);


    private LoadingCache<PK, V> cache = CacheBuilder.newBuilder()
            //设置并发级别为8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            //设置缓存容器的初始容量为10
            .initialCapacity(100)
            //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(1000)
            //是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
            .recordStats()
            //设置写缓存后n秒钟过期
            .expireAfterWrite(6000, TimeUnit.SECONDS)
            //设置缓存的移除通知
            .removalListener(notification -> {
                System.out.println(notification.getKey() + " " + notification.getValue() + " 被移除,原因:" + notification.getCause());
            })
            //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
            .build(new EntityCacheLoader<>());

    public EntityCacheServiceImpl() {

    }

    public Class<V> getClazz() {
        return clazz;
    }

    public LoadingCache<PK, V> getCache() {
        return cache;
    }


    public void setClazz(Class<V> clazz) {
        this.clazz = clazz;
    }

    public void setCache(LoadingCache<PK, V> cache) {
        this.cache = cache;
    }

    @Override
    public synchronized V load(PK id) {
        V entity = null;
        try {
            entity = cache.get(id);
            if (entity == null) {
                entity = accessor.load(clazz, id);
                //反序列化
                entity.deSerialize();
                cache.put(id, entity);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void writeBack(PK id, V entity) {
        //更细操作异步写回 先不考虑
        //consumer.put(Element.updateof(entity));

        //序列化
        entity.serialize();
        accessor.saveOrUpdate(clazz, entity);
    }

    @Override
    public synchronized V loadOrCreate(PK id, EntityBuilder<PK, V> builder) {
        V entity = null;
        try {
            entity = cache.get(id);
            if (entity instanceof EmptyEntity) {
                entity = accessor.load(clazz, id);
                if (null != entity) {
                    entity.deSerialize();
                }

                if (null == entity) {
                    entity = builder.newInstance(id);
                    //序列化
                    entity.serialize();
                    id = accessor.save(clazz, entity);
                }
                cache.put(id, entity);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public void setAccessor(Accessor accessor) {
        this.accessor = accessor;
    }

    public void setConsumer(QueueConsumer consumer) {
        this.consumer = consumer;
    }

    public Accessor getAccessor() {
        return accessor;
    }

    public QueueConsumer getConsumer() {
        return consumer;
    }
}