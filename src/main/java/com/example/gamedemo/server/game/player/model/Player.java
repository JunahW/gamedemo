package com.example.gamedemo.server.game.player.model;

import com.example.gamedemo.server.game.SpringContext;
import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import com.example.gamedemo.server.game.equip.entity.EquipStorageEnt;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import com.example.gamedemo.server.game.scene.model.Scene;
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
     * 场景
     */
    private Scene scene;


    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAccountId() {
        return accountId;
    }

    public Scene getScene() {
        return scene;
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

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
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
