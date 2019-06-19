package com.example.gamedemo.server.game.scene.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;

/**
 * @author wengj
 * @description：地形资源
 * @date 2019/6/19
 */
@Resource
public class LandformResource implements ResourceInterface {
  /** 配置资源的id */
  @ExcelColumn(columnName = "landformId")
  private int landformId;

  /** 地图的宽 */
  @ExcelColumn(columnName = "width")
  private int width;

  /** 地图的高 */
  @ExcelColumn(columnName = "height")
  private int height;

  /** 地图数组字符串 */
  @ExcelColumn(columnName = "mapArray")
  private int[][] mapArray;

  @Override
  public Object getId() {
    return landformId;
  }

  @Override
  public void postInit() {}

  public int getLandformId() {
    return landformId;
  }

  public void setLandformId(int landformId) {
    this.landformId = landformId;
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

  public int[][] getMapArray() {
    return mapArray;
  }

  public void setMapArray(int[][] mapArray) {
    this.mapArray = mapArray;
  }
}
