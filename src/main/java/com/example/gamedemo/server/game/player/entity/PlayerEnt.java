package com.example.gamedemo.server.game.player.entity;

import com.example.gamedemo.common.ramcache.Entity;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.player.model.Player;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description:
 */
@Table
@javax.persistence.Entity
public class PlayerEnt implements Entity<String> {
    @Id
    private String playerId;

    @Column
    private String playerName;

    @Column
    private String accountId;

    @Column
    private int playerType;

    /**
     * x轴位置
     */
    @Column
    private int x;

    /**
     * y轴位置
     */
    @Column
    private int y;

    @Column(length = 10000)
    private String sceneData;

    /**
     * 场景
     */

    @Transient
    private Player player;


    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAccountId() {
        return accountId;
    }

    public int getPlayerType() {
        return playerType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getSceneData() {
        return sceneData;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSceneData(String sceneData) {
        this.sceneData = sceneData;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String getId() {
        return this.playerId;
    }

    @Override
    public void setNullId() {
        this.player = null;
    }

    @Override
    public boolean serialize() {
        String sceneString = JsonUtils.serializeEntity(player.getSceneResource());
        this.setSceneData(sceneString);
        this.setPlayerId(player.getPlayerId());
        this.setPlayerName(player.getPlayerName());
        this.setAccountId(player.getAccountId());
        this.setPlayerType(player.getPlayerType());
        return true;
    }

    @Override
    public boolean deSerialize() {
        //TODO 序列化有问题
        //SceneResource sceneResource = JsonUtils.deSerializeEntity(getSceneData(), SceneResource.class);
        Player player = new Player();
        //player.setSceneResource(sceneResource);
        player.setPlayerId(getPlayerId());
        player.setPlayerName(getPlayerName());
        player.setAccountId(getAccountId());
        player.setPlayerType(getPlayerType());
        this.setPlayer(player);
        return true;
    }
}
