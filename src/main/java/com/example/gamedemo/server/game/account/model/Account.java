package com.example.gamedemo.server.game.account.model;

import com.example.gamedemo.server.game.scene.model.Scene;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author: wengj
 * @date: 2019/4/25
 * @description: 用户账号
 */
public class Account implements Serializable {
    private String countId;
    private String countName;

    private Scene scene;

    public String getCountId() {
        return countId;
    }

    public String getCountName() {
        return countName;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setCountId(String countId) {
        this.countId = countId;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(countId, account.countId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "countId='" + countId + '\'' +
                ", countName='" + countName + '\'' +
                '}';
    }
}
