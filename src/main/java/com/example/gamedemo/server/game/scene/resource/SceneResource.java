package com.example.gamedemo.server.game.scene.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.resource.ResourceInterface;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description:
 */
@Resource
public class SceneResource implements Serializable, ResourceInterface {
  /** 场景id */
  @ExcelColumn(columnName = "sceneId")
  private int sceneId;

  /** 场景名称 */
  @ExcelColumn(columnName = "sceneName")
  private String sceneName;

  /** 临近场景 用‘,’隔开 */
  @ExcelColumn(columnName = "neighbors")
  private String neighbors;

  /** 地图的宽 */
  @ExcelColumn(columnName = "width")
  private int width;

  /** 地图的高 */
  @ExcelColumn(columnName = "height")
  private int height;

  /** 出生地坐标x */
  @ExcelColumn(columnName = "x")
  private int x;

  /** 出生地坐标y */
  @ExcelColumn(columnName = "y")
  private int y;

  /** 地图数组字符串 */
  @ExcelColumn(columnName = "sceneMap")
  private int[][] sceneMap;

  /** NPC字符串集合，“,”隔开 */
  @ExcelColumn(columnName = "npcs")
  private String npcs;

  private int[] npcArray;

  /** 怪物字符串集合，“,”隔开 */
  @ExcelColumn(columnName = "monsters")
  private String monsters;

  private int[] monsterArray;

  public SceneResource() {}

  public SceneResource(int sceneId) {
    this.sceneId = sceneId;
  }

  public SceneResource(int sceneId, String sceneName) {
    this.sceneId = sceneId;
    this.sceneName = sceneName;
  }

  public int getSceneId() {
    return sceneId;
  }

  public void setSceneId(int sceneId) {
    this.sceneId = sceneId;
  }

  public String getSceneName() {
    return sceneName;
  }

  public void setSceneName(String sceneName) {
    this.sceneName = sceneName;
  }

  public String getNeighbors() {
    return neighbors;
  }

  public void setNeighbors(String neighbors) {
    this.neighbors = neighbors;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public String getNpcs() {
    return npcs;
  }

  public void setNpcs(String npcs) {
    this.npcs = npcs;
  }

  public String getMonsters() {
    return monsters;
  }

  public void setMonsters(String monsters) {
    this.monsters = monsters;
  }

  public int[][] getSceneMap() {
    return sceneMap;
  }

  public void setSceneMap(int[][] sceneMap) {
    this.sceneMap = sceneMap;
  }

  public int[] getNpcArray() {
    return npcArray;
  }

  public void setNpcArray(int[] npcArray) {
    this.npcArray = npcArray;
  }

  public int[] getMonsterArray() {
    return monsterArray;
  }

  public void setMonsterArray(int[] monsterArray) {
    this.monsterArray = monsterArray;
  }

  @Override
  public String toString() {
    return "Scene{"
        + "sceneId='"
        + sceneId
        + '\''
        + ", sceneName='"
        + sceneName
        + '\''
        + ", neighbors='"
        + neighbors
        + '\''
        + ", width="
        + width
        + ", height="
        + height
        + ", x="
        + x
        + ", y="
        + y
        + ", sceneMap="
        + Arrays.toString(sceneMap)
        + ", npcs='"
        + npcs
        + '\''
        + ", monsters='"
        + monsters
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SceneResource sceneResource = (SceneResource) o;
    return Objects.equals(sceneId, sceneResource.sceneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sceneId);
  }

  @JsonIgnore
  @Override
  public Object getId() {
    return this.sceneId;
  }

  @Override
  public void postInit() {
    String[] npcSplit = npcs.split(SystemConstant.SPLIT_TOKEN_COMMA);
    int[] npcTmp = new int[npcSplit.length];
    for (int i = 0; i < npcSplit.length; i++) {
      npcTmp[i] = Integer.parseInt(npcSplit[i]);
    }
    npcArray = npcTmp;

    String[] monsterSplit = monsters.split(SystemConstant.SPLIT_TOKEN_COMMA);
    int[] monsterTmp = new int[monsterSplit.length];
    for (int i = 0; i < monsterSplit.length; i++) {
      monsterTmp[i] = Integer.parseInt(monsterSplit[i]);
    }
    monsterArray = monsterTmp;
  }
}
