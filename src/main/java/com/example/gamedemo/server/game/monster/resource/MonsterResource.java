package com.example.gamedemo.server.game.monster.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;

/**
 * @author wengj
 * @description：怪物
 * @date 2019/5/21
 */
@Resource
public class MonsterResource implements ResourceInterface {
  /** 实体id */
  @ExcelColumn(columnName = "monsterId")
  private int monsterId;

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

  /** 血量 */
  @ExcelColumn(columnName = "hp")
  private int hp;

  /** 魔法值 */
  @ExcelColumn(columnName = "mp")
  private int mp;

  /** 攻击力 */
  @ExcelColumn(columnName = "attack")
  private int attack;

  @Override
  public Object getId() {
    return this.monsterId;
  }

  @Override
  public void postInit() {}

  public int getMonsterId() {
    return monsterId;
  }

  public void setMonsterId(int monsterId) {
    this.monsterId = monsterId;
  }

  public String getObjectName() {
    return objectName;
  }

  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
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

  public String getSceneId() {
    return sceneId;
  }

  public void setSceneId(String sceneId) {
    this.sceneId = sceneId;
  }

  public int getHp() {
    return hp;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public int getMp() {
    return mp;
  }

  public void setMp(int mp) {
    this.mp = mp;
  }

  public int getAttack() {
    return attack;
  }

  public void setAttack(int attack) {
    this.attack = attack;
  }
}
