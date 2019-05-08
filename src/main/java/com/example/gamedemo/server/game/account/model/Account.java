package com.example.gamedemo.server.game.account.model;

import com.alibaba.fastjson.JSON;
import com.example.gamedemo.server.game.scene.model.Scene;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author: wengj
 * @date: 2019/4/25
 * @description: 用户账号
 */
public class Account implements Serializable {
    private String acountId;
    private String acountName;

    private Scene scene = new Scene("s2001");

    public String getAcountId() {
        return acountId;
    }

    public String getAcountName() {
        return acountName;
    }

    public Scene getScene() {
        return scene;
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

    @Override
    public String toString() {
        return "Account{" +
                "acountId='" + acountId + '\'' +
                ", acountName='" + acountName + '\'' +
                ", scene=" + scene.getSceneName() +
                '}';
    }
}
