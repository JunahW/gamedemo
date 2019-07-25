package com.example.gamedemo.server.game.scene.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.resource.ResourceInterface;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.base.resource.bean.CheckPointDef;
import com.example.gamedemo.server.game.base.resource.bean.RewardDef;
import com.example.gamedemo.server.game.scene.constant.SceneTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description:
 */
@Resource
public class MapResource implements Serializable, ResourceInterface {
  /** 场景id */
  @ExcelColumn(columnName = "mapId")
  private int mapId;

  /** 场景名称 */
  @ExcelColumn(columnName = "mapName")
  private String mapName;

  /** 临近场景 用‘,’隔开 */
  @ExcelColumn(columnName = "neighbors")
  private String neighbors;

  /** 相邻场景 */
  private int[] neighborArray;

  /** 出生地坐标x */
  @ExcelColumn(columnName = "x")
  private int x;

  /** 出生地坐标y */
  @ExcelColumn(columnName = "y")
  private int y;

  /** 地形id */
  @ExcelColumn(columnName = "landformId")
  private int landformId;

  /** 地图持续时间 */
  @ExcelColumn(columnName = "duration")
  private long duration;

  /** 场景类型 */
  @ExcelColumn(columnName = "sceneType")
  private int sceneType;

  @ExcelColumn(columnName = "rewardString")
  private String rewardString;

  /** cd时间 */
  @ExcelColumn(columnName = "cd")
  private long cd;

  private List<RewardDef> rewardDefs;

  private SceneTypeEnum sceneTypeEnum;

  @ExcelColumn(columnName = "checkPointString")
  private String checkPointString;

  /** 回合定义 链表结构 */
  private List<CheckPointDef> checkPointDefList;

  public MapResource() {}

  public MapResource(int mapId) {
    this.mapId = mapId;
  }

  public MapResource(int mapId, String mapName) {
    this.mapId = mapId;
    this.mapName = mapName;
  }

  public int getMapId() {
    return mapId;
  }

  public void setMapId(int mapId) {
    this.mapId = mapId;
  }

  public String getMapName() {
    return mapName;
  }

  public void setMapName(String mapName) {
    this.mapName = mapName;
  }

  public String getNeighbors() {
    return neighbors;
  }

  public void setNeighbors(String neighbors) {
    this.neighbors = neighbors;
  }

  public int[] getNeighborArray() {
    return neighborArray;
  }

  public void setNeighborArray(int[] neighborArray) {
    this.neighborArray = neighborArray;
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

  public int getLandformId() {
    return landformId;
  }

  public void setLandformId(int landformId) {
    this.landformId = landformId;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public SceneTypeEnum getSceneTypeEnum() {
    return sceneTypeEnum;
  }

  public void setSceneTypeEnum(SceneTypeEnum sceneTypeEnum) {
    this.sceneTypeEnum = sceneTypeEnum;
  }

  public List<RewardDef> getRewardDefs() {
    return rewardDefs;
  }

  public void setRewardDefs(List<RewardDef> rewardDefs) {
    this.rewardDefs = rewardDefs;
  }

  public long getCd() {
    return cd;
  }

  public void setCd(long cd) {
    this.cd = cd;
  }

  public List<CheckPointDef> getCheckPointDefList() {
    return checkPointDefList;
  }

  public void setCheckPointDefList(List<CheckPointDef> checkPointDefList) {
    this.checkPointDefList = checkPointDefList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MapResource mapResource = (MapResource) o;
    return Objects.equals(mapId, mapResource.mapId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mapId);
  }

  @JsonIgnore
  @Override
  public Object getId() {
    return this.mapId;
  }

  @Override
  public void postInit() {
    if (!StringUtils.isEmpty(neighbors)) {
      String[] neighborSplit = neighbors.split(SystemConstant.SPLIT_TOKEN_COMMA);
      int[] neighborTmp = new int[neighborSplit.length];
      for (int i = 0; i < neighborSplit.length; i++) {
        neighborTmp[i] = Integer.parseInt(neighborSplit[i]);
      }
      neighborArray = neighborTmp;
    }
    sceneTypeEnum = SceneTypeEnum.getSceneTypeEnumBySceneType(sceneType);
    if (!StringUtils.isEmpty(rewardString)) {
      rewardDefs = JsonUtils.getListByString(rewardString, new TypeReference<List<RewardDef>>() {});
    }

    if (!StringUtils.isEmpty(checkPointString)) {
      checkPointDefList =
          JsonUtils.getListByString(checkPointString, new TypeReference<List<CheckPointDef>>() {});
    } else {
      checkPointDefList = new ArrayList<>();
    }
  }
}
