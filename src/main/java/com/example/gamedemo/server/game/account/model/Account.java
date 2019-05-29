package com.example.gamedemo.server.game.account.model;

import com.example.gamedemo.server.game.SpringContext;
import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.model.ItemStorage;
import com.example.gamedemo.server.game.scene.model.Scene;

import java.io.Serializable;

/**
 * @author: wengj
 * @date: 2019/4/25
 * @description: 用户账号
 */
public class Account implements Serializable {
    private String acountId;
    private String acountName;

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

    /**
     * 背包
     */
    private ItemStorage itemStorage = new ItemStorage();

    public String getAcountId() {
        return acountId;
    }

    public String getAcountName() {
        return acountName;
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

    public ItemStorage getItemStorage() {
        return itemStorage;
    }

    public void setAcountId(String acountId) {
        this.acountId = acountId;
    }

    public void setAcountName(String acountName) {
        this.acountName = acountName;
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

    public void setItemStorage(ItemStorage itemStorage) {
        this.itemStorage = itemStorage;
    }

    public ItemStorage getPack() {
        ItemStorageEnt pack = SpringContext.getItemService().getItemStorageEntByAccountId(acountId);
        return pack.getItemStorage();
    }

    @Override
    public String toString() {
        return "Account{" +
                "acountId='" + acountId + '\'' +
                ", acountName='" + acountName + '\'' +
                ", x=" + x +
                ", y=" + y +
                // ", scene=" + scene.getSceneName() +
                '}';
    }
}
