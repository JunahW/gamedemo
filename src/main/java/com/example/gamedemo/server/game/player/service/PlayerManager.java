package com.example.gamedemo.server.game.player.service;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.SystemInitializer;
import com.example.gamedemo.server.game.player.entity.PlayerEnt;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.player.resource.BaseAttributeResource;
import com.example.gamedemo.server.game.player.resource.PlayerResource;
import com.example.gamedemo.server.game.scene.resource.SceneResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;
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

    static {
        SystemInitializer.initResource();
    }


    /**
     * 所有账户
     */
    private static ConcurrentHashMap<String, Player> accountId2AccountMap = new ConcurrentHashMap<String, Player>();

    /**
     * 通过id获取账户信息
     *
     * @param accountId
     * @return
     */
    public static Player getAccountById(String accountId) {
        return accountId2AccountMap.get(accountId);
    }

    /**
     * 添加或者账户信息修改
     *
     * @param player
     */
    public static void setAccount(Player player) {
        if (null != player) {
            accountId2AccountMap.put(player.getPlayerId(), player);
        }
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


}
