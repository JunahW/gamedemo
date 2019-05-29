package com.example.gamedemo.server.game.bag.service;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityBuilder;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.SystemInitializer;
import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.model.ItemStorage;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description
 * @date 2019/5/29
 */
@Component
public class ItemManager {

    /**
     * 静态资源
     */
    private ConcurrentMap<String, ItemResource> itemResource = ResourceManager.getResourceMap(ItemResource.class);

    static {
        SystemInitializer.initControllerMap();
        SystemInitializer.initResource();
    }

    @Autowired
    private Accessor accessor;

    private final Class clazz = ItemStorageEnt.class;

    private EntityCacheServiceImpl<String, ItemStorageEnt> entityCacheService = new EntityCacheServiceImpl<>();

    @PostConstruct
    public void init() {
        entityCacheService.setClazz(clazz);
        entityCacheService.setAccessor(accessor);
    }


    /**
     * 通过id获取配置的项
     *
     * @param itemId
     * @return
     */
    public ItemResource getResourceById(String itemId) {
        return itemResource.get(itemId);
    }

    public ItemStorageEnt getItemStorageEnt(String accountId) {
        ItemStorageEnt storageEnt = entityCacheService.loadOrCreate(accountId, new EntityBuilder<String, ItemStorageEnt>() {
            @Override
            public ItemStorageEnt newInstance(String id) {

                ItemStorageEnt itemStorageEnt = new ItemStorageEnt();
                ItemStorage pack = new ItemStorage();

                itemStorageEnt.setItemStorage(pack);
                itemStorageEnt.setAccountId(accountId);
                return itemStorageEnt;
            }
        });
        return storageEnt;

    }
}
