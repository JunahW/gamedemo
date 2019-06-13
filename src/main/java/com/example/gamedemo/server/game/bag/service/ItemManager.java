package com.example.gamedemo.server.game.bag.service;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityBuilder;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
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
    private ConcurrentMap<String, ItemResource> itemResource =
            ResourceManager.getResourceMap(ItemResource.class);

    static {
        ResourceManager.initResource();
    }

    @Autowired
    private Accessor accessor;

    private final Class clazz = ItemStorageEnt.class;

    private EntityCacheServiceImpl<String, ItemStorageEnt> entityCacheService =
            new EntityCacheServiceImpl<>();

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
    public ItemResource getResourceById(int itemId) {
        return itemResource.get(itemId);
    }

    /**
     * 获取背包
     *
     * @param accountId
     * @return
     */
    public ItemStorageEnt getItemStorageEnt(String accountId) {
        ItemStorageEnt storageEnt =
                entityCacheService.loadOrCreate(
                        accountId,
                        new EntityBuilder<String, ItemStorageEnt>() {
                            @Override
                            public ItemStorageEnt newInstance(String id) {

                ItemStorageEnt itemStorageEnt = new ItemStorageEnt();
                ItemStorage pack = new ItemStorage();

                itemStorageEnt.setItemStorage(pack);
                itemStorageEnt.setAccountId(accountId);
                                // FIXME 去除 itemStorageEnt.serialize();
                                return itemStorageEnt;
                            }
                        });
        return storageEnt;
    }

    /**
     * 保存背包信息
     *
     * @param itemStorageEnt
     */
    public void saveItemStorageEnt(ItemStorageEnt itemStorageEnt) {
        // FIXME 去除 itemStorageEnt.serialize();
        entityCacheService.writeBack(itemStorageEnt.getId(), itemStorageEnt);
  }
}
