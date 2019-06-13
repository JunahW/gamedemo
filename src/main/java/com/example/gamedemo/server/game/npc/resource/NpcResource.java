package com.example.gamedemo.server.game.npc.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;

import java.util.Objects;

/**
 * @author wengj
 * @description
 * @date 2019/5/5
 */
@Resource
public class NpcResource implements ResourceInterface {
  /** 实体id */
  @ExcelColumn(columnName = "npcId")
  private int npcId;
  /** 实体名称 */
  @ExcelColumn(columnName = "objectName")
  private String objectName;
  /** 实体状态 0 死亡 1 生存 */
  @ExcelColumn(columnName = "status")
  private int status;
  /** 所在的位置x值 */
  @ExcelColumn(columnName = "x")
  private int x;
  /** 所在的位置y值 */
  @ExcelColumn(columnName = "y")
  private int y;
  /** 场景id */
  @ExcelColumn(columnName = "sceneId")
  private String sceneId;

  public NpcResource(int npcId, String objectName, int status) {
    this.npcId = npcId;
    this.objectName = objectName;
    this.status = status;
  }

  public NpcResource() {}

  public int getNpcId() {
    return npcId;
  }

  public void setNpcId(int npcId) {
    this.npcId = npcId;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getObjectName() {
    return objectName;
  }

  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  public String getSceneId() {
    return sceneId;
  }

  public void setSceneId(String sceneId) {
    this.sceneId = sceneId;
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

  @Override
  public String toString() {
    return "NpcResource{"
        + "npcId="
        + npcId
        + ", objectName='"
        + objectName
        + '\''
        + ", status="
        + status
        + ", x="
        + x
        + ", y="
        + y
        + ", sceneId='"
        + sceneId
        + '\''
        + '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(npcId);
  }

  @Override
  public Object getId() {
    return this.npcId;
  }

  @Override
  public void postInit() {}
}
