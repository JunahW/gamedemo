package com.example.gamedemo.server.game.scene.model;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;
import com.example.gamedemo.server.game.player.model.Player;
import io.netty.util.internal.ConcurrentSet;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description:
 */
@Resource
public class Scene implements Serializable, ResourceInterface {
    /**
     * 场景id
     */
    @ExcelColumn(columnName = "sceneId")
    private String sceneId;

    /**
     * 场景名称
     */
    @ExcelColumn(columnName = "sceneName")
    private String sceneName;

    /**
     * 临近场景 用‘,’隔开
     */
    @ExcelColumn(columnName = "neighbors")
    private String neighbors;


    /**
     * 地图的宽
     */
    @ExcelColumn(columnName = "width")
    private int width;

    /**
     * 地图的高
     */
    @ExcelColumn(columnName = "height")
    private int height;

    /**
     * 出生地坐标x
     */
    @ExcelColumn(columnName = "x")
    private int x;

    /**
     * 出生地坐标y
     */
    @ExcelColumn(columnName = "y")
    private int y;

    /**
     * 地图数组字符串
     */
    @ExcelColumn(columnName = "sceneMap")
    private int[][] sceneMap;


    /**
     * NPC字符串集合，“,”隔开
     */
    @ExcelColumn(columnName = "npcs")
    private String npcs;

    /**
     * 怪物字符串集合，“,”隔开
     */
    @ExcelColumn(columnName = "monsters")
    private String monsters;

    /**
     * 场景上的用户
     */
    private ConcurrentSet<Player> playerSet = new ConcurrentSet<>();

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

    public ConcurrentSet<Player> getPlayerSet() {
        return playerSet;
    }

    public ConcurrentSet<Npc> getNpcSet() {
        return npcSet;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getNpcs() {
        return npcs;
    }

    public String getMonsters() {
        return monsters;
    }

    public int[][] getSceneMap() {
        return sceneMap;
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

    public void setPlayerSet(ConcurrentSet<Player> playerSet) {
        this.playerSet = playerSet;
    }

    public void setNpcSet(ConcurrentSet<Npc> npcSet) {
        this.npcSet = npcSet;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSceneMap(int[][] sceneMap) {
        this.sceneMap = sceneMap;
    }

    public void setNpcs(String npcs) {
        this.npcs = npcs;
    }

    public void setMonsters(String monsters) {
        this.monsters = monsters;
    }

    public Scene() {
    }

    public Scene(String sceneId) {
        this.sceneId = sceneId;
    }

    public Scene(String sceneId, String sceneName) {
        this.sceneId = sceneId;
        this.sceneName = sceneName;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "sceneId='" + sceneId + '\'' +
                ", sceneName='" + sceneName + '\'' +
                ", neighbors='" + neighbors + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", x=" + x +
                ", y=" + y +
                ", sceneMap=" + Arrays.toString(sceneMap) +
                ", npcs='" + npcs + '\'' +
                ", monsters='" + monsters + '\'' +
                ", accountSet=" + playerSet +
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

    @Override
    public Object getId() {
        return this.sceneId;
    }
}
