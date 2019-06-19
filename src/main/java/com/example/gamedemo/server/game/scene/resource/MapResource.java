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
    String[] neighborSplit = neighbors.split(SystemConstant.SPLIT_TOKEN_COMMA);
    int[] neighborTmp = new int[neighborSplit.length];
    for (int i = 0; i < neighborSplit.length; i++) {
      neighborTmp[i] = Integer.parseInt(neighborSplit[i]);
    }
    neighborArray = neighborTmp;
  }

  @Override
  public String toString() {
    return "MapResource{"
        + "mapId="
        + mapId
        + ", mapName='"
        + mapName
        + '\''
        + ", neighbors='"
        + neighbors
        + '\''
        + ", neighborArray="
        + Arrays.toString(neighborArray)
        + ", x="
        + x
        + ", y="
        + y
        + ", landformId="
        + landformId
        + '}';
  }
}
