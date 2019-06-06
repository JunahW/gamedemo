package com.example.gamedemo.server.game.player.service;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.player.entity.PlayerEnt;
import com.example.gamedemo.server.game.player.resource.BaseAttributeResource;
import com.example.gamedemo.server.game.player.resource.PlayerResource;
import com.example.gamedemo.server.game.scene.resource.SceneResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: wengj
 * @date: 2019/4/28
 * @description:
 */
@Component
public class PlayerManager {

    private ConcurrentMap<String, PlayerResource> playerResource = ResourceManager.getResourceMap(PlayerResource.class);

    private ConcurrentMap<String, BaseAttributeResource> baseAttributeResource = ResourceManager.getResourceMap(BaseAttributeResource.class);

    private ConcurrentMap<String, SceneResource> sceneResource = ResourceManager.getResourceMap(SceneResource.class);


    @Autowired
    private Accessor accessor;

    private EntityCacheServiceImpl<String, PlayerEnt> entityCacheService = new EntityCacheServiceImpl<>();


    @PostConstruct
    public void init() {
        entityCacheService.setClazz(PlayerEnt.class);
        entityCacheService.setAccessor(accessor);
    }

    /**
     * 获取玩家配置信息
     *
     * @param modeId
     * @return
     */
    public PlayerResource getPlayerResourceById(String modeId) {
        return playerResource.get(modeId);
    }


    /**
     * 获取玩家的基础属性
     *
     * @param playerType
     * @return
     */
    public BaseAttributeResource getAttributeResourceByPlayerType(String playerType) {
        return baseAttributeResource.get(playerType);
    }

    /**
     * 通过id获取场景
     *
     * @param sceneId
     * @return
     */
    public SceneResource getSceneResourceById(String sceneId) {
        return sceneResource.get(sceneId);
    }


    /**
     * 获取玩家信息
     *
     * @param playerId
     * @return
     */
    public PlayerEnt getPlayerEntByPlayerId(String playerId) {
        return entityCacheService.load(playerId);
    }


}
