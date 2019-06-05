package com.example.gamedemo.server.game.player.model;

import com.example.gamedemo.server.game.SpringContext;
import com.example.gamedemo.server.game.attribute.PlayerAttributeContainer;
import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import com.example.gamedemo.server.game.equip.entity.EquipStorageEnt;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import com.example.gamedemo.server.game.scene.resource.SceneResource;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author: wengj
 * @date: 2019/4/25
 * @description: 用户账号
 */
public class Player implements Serializable {
    /**
     * 玩家id
     */
    private String playerId;

    /**
     * 玩家名称
     */
    private String playerName;

    /**
     * 玩家所对应的账户id
     */
    private String accountId;

    /**
     * 玩家的类型
     */
    private String playerType;

    /**
     * x轴位置
     */
    private int x;

    /**
     * y轴位置
     */
    private int y;

    /**
     * 玩家属性容器
     */
    private PlayerAttributeContainer playerAttributeContainer = new PlayerAttributeContainer();

    /**
     * 场景
     */
    private SceneResource sceneResource;


    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAccountId() {
        return accountId;
    }

    public SceneResource getSceneResource() {
        return sceneResource;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public String getPlayerType() {
        return playerType;
    }

    public PlayerAttributeContainer getPlayerAttributeContainer() {
        return playerAttributeContainer;
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

    public void setSceneResource(SceneResource sceneResource) {
        this.sceneResource = sceneResource;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public void setPlayerAttributeContainer(PlayerAttributeContainer playerAttributeContainer) {
        this.playerAttributeContainer = playerAttributeContainer;
    }

    /**
     * 获取背包
     *
     * @return
     */
    @JsonIgnore
    public ItemStorage getPack() {
        ItemStorageEnt pack = SpringContext.getItemService().getItemStorageEntByAccountId(accountId);
        return pack.getItemStorage();
    }

    /**
     * 获取装备栏
     *
     * @return
     */
    @JsonIgnore
    public EquipStorage getEquipBar() {
        EquipStorageEnt equipStorageEnt = SpringContext.getEquipmentService().getEquipStorageEnt(playerId);
        return equipStorageEnt.getEquipStorage();
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId='" + playerId + '\'' +
                ", playerName='" + playerName + '\'' +
                ", accountId='" + accountId + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

}
