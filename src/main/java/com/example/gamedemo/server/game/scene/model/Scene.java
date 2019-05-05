package com.example.gamedemo.server.game.scene.model;

import com.example.gamedemo.server.game.account.model.Account;
import io.netty.util.internal.ConcurrentSet;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description:
 */
public class Scene implements Serializable {
    /**
     * 场景id
     */
    private String sceneId;

    /**
     * 场景名称
     */
    private String sceneName;

    /**
     * 临近场景 用‘,’隔开
     */
    private String neighbors;

    /**
     * 场景上的用户
     */
    private ConcurrentSet<Account> accountSet = new ConcurrentSet<>();

    /**
     * 场景上的npc
     */
    private ConcurrentSet<Npc> npcSet = new ConcurrentSet<>();


    public String getSceneId() {
        return sceneId;
    }

    public String getSceneName() {
        return sceneName;
    }

    public String getNeighbors() {
        return neighbors;
    }

    public ConcurrentSet<Account> getAccountSet() {
        return accountSet;
    }

    public ConcurrentSet<Npc> getNpcSet() {
        return npcSet;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public void setNeighbors(String neighbors) {
        this.neighbors = neighbors;
    }

    public void setAccountSet(ConcurrentSet<Account> accountSet) {
        this.accountSet = accountSet;
    }

    public void setNpcSet(ConcurrentSet<Npc> npcSet) {
        this.npcSet = npcSet;
    }

    public Scene() {
    }

    public Scene(String sceneId) {
        this.sceneId = sceneId;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "sceneId='" + sceneId + '\'' +
                ", sceneName='" + sceneName + '\'' +
                ", neighbors='" + neighbors + '\'' +
                ", accountSet=" + accountSet +
                ", npcSet=" + npcSet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Scene scene = (Scene) o;
        return Objects.equals(sceneId, scene.sceneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sceneId);
    }
}
