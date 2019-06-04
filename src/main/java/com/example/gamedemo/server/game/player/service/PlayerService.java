package com.example.gamedemo.server.game.player.service;

import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.player.resource.BaseAttributeResource;

import java.util.List;

/**
 * @author: wengj
 * @date: 2019/4/28
 * @description: 账户业务层接口
 */
public interface PlayerService {

    /**
     * 通过id获取账户信息
     *
     * @param accountId
     * @return
     */
    Player getAccountById(String accountId);

    /**
     * 新增账户
     *
     * @param player
     * @return
     */
    int createPlayer(Player player);

    /**
     * 账户登录
     *
     * @param playerId
     * @return
     */
    Player selectPlayer(String playerId);

    /**
     * 更细账户
     *
     * @param player
     */
    void updateAccount(Player player);

    /**
     * 获取登陆账户的集合
     *
     * @return
     */
    List<Player> getAccountList();

    /**
     * 玩家移动
     *
     * @param player
     * @param x
     * @param y
     * @return
     */
    boolean move2Coordinate(Player player, int x, int y);

    /**
     * 玩家类型
     *
     * @param playerType
     * @return
     */
    BaseAttributeResource getBaseAttributeResourceByPlayerType(String playerType);


}
