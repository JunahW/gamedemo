package com.example.gamedemo.server.game.equip.service;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityBuilder;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.server.game.equip.entity.EquipStorageEnt;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wengj
 * @description：装备资源管理层
 * @date 2019/5/30
 */
@Component
public class EquipmentManager {

    @Autowired
    private Accessor accessor;

    private EntityCacheServiceImpl<String, EquipStorageEnt> entityCacheService = new EntityCacheServiceImpl<>();

    @PostConstruct
    public void init() {
        entityCacheService.setClazz(EquipStorageEnt.class);
        entityCacheService.setAccessor(accessor);
    }

    /**
     * 获取装备栏
     *
     * @param playerId
     * @return
     */
    public EquipStorageEnt getEquipStorageEnt(String playerId) {
        EquipStorageEnt equipStorageEnt = entityCacheService.loadOrCreate(playerId, new EntityBuilder<String, EquipStorageEnt>() {
            @Override
            public EquipStorageEnt newInstance(String id) {
                EquipStorageEnt equipStorageEnt = new EquipStorageEnt();
                EquipStorage bar = new EquipStorage();

                equipStorageEnt.setEquipStorage(bar);
                equipStorageEnt.setPlayerId(playerId);
                //FIXME 去除 equipStorageEnt.serialize();
                return equipStorageEnt;
            }
        });
        return equipStorageEnt;

    }

    /**
     * 存储至数据库
     *
     * @param equipStorageEnt
     */
    public void saveEquipStorageEnt(EquipStorageEnt equipStorageEnt) {
        //FIXME equipStorageEnt.serialize();
        entityCacheService.writeBack(equipStorageEnt.getId(), equipStorageEnt);
    }
}
