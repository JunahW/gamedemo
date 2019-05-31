package com.example.gamedemo.server.game.player.service;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.SystemInitializer;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.player.resource.PlayerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
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

    static {
        SystemInitializer.initResource();
    }

    @Autowired
    private PlayerService playerService;


    /**
     * 所有账户
     */
    private static ConcurrentHashMap<String, Player> accountId2AccountMap = new ConcurrentHashMap<String, Player>();

    /**
     * 在线账户
     */
    private static ConcurrentHashMap<String, Player> loginAccountMap = new ConcurrentHashMap<String, Player>();

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
     * 通过ID获取已登录账户信息
     *
     * @param accountId
     * @return
     */
    public static Player getLoginAccountById(String accountId) {
        return loginAccountMap.get(accountId);
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
     * 新曾在线用户
     *
     * @param player
     */
    public static void setLoginAccount(Player player) {
        loginAccountMap.put(player.getPlayerId(), player);
    }

    public static ConcurrentHashMap<String, Player> getAccountId2AccountMap() {
        return accountId2AccountMap;
    }

    public static ConcurrentHashMap<String, Player> getLoginAccountMap() {
        return loginAccountMap;
    }

    /**
     * 获取所有登陆用户
     *
     * @return
     */
    public static List<Player> getLoginAccountList() {
        LinkedList<Player> list = new LinkedList<Player>();
        Set<Map.Entry<String, Player>> entries = loginAccountMap.entrySet();
        Iterator<Map.Entry<String, Player>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getValue());
        }

        return list;
    }

    /**
     * 将用户信息加载到内存
     */
    @PostConstruct
    public void initAccountMap() {
        List<Player> playerList = playerService.getAccountList();
        for (Player player : playerList) {
            PlayerManager.setAccount(player);
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
}
