package com.example.gamedemo.server.game.monster.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.common.model.Drop;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

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

  /** 数量 */
  @ExcelColumn(columnName = "quantity")
  private int quantity;

  /** 所在的位置x值 */
  @ExcelColumn(columnName = "x")
  private int x;

  /** 所在的位置y值 */
  @ExcelColumn(columnName = "y")
  private int y;

  /** 场景id */
  @ExcelColumn(columnName = "mapId")
  private int mapId;

  /** 血量 */
  @ExcelColumn(columnName = "hp")
  private int hp;

  /** 魔法值 */
  @ExcelColumn(columnName = "mp")
  private int mp;

  /** 攻击力 */
  @ExcelColumn(columnName = "attack")
  private int attack;

  /** 攻击力 */
  @ExcelColumn(columnName = "defense")
  private int defense;

  /** 死亡经验值 */
  @ExcelColumn(columnName = "exp")
  private long exp;

  /** 掉落物 */
  @ExcelColumn(columnName = "dropObjectData")
  private String dropObjectData;

  private List<Drop> dropList;

  @ExcelColumn(columnName = "attrs")
  private String attrs;

  private List<Attribute> attributeList;

  @Override
  public Object getId() {
    return this.monsterId;
  }

  @Override
  public void postInit() {
    if (dropObjectData != null) {
      this.setDropList(
          JsonUtils.getListByString(getDropObjectData(), new TypeReference<List<Drop>>() {}));
    }

    this.setAttributeList(
        JsonUtils.getListByString(getAttrs(), new TypeReference<List<Attribute>>() {}));
  }

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

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
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

  public int getMapId() {
    return mapId;
  }

  public void setMapId(int mapId) {
    this.mapId = mapId;
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

  public int getDefense() {
    return defense;
  }

  public void setDefense(int defense) {
    this.defense = defense;
  }

  public long getExp() {
    return exp;
  }

  public void setExp(long exp) {
    this.exp = exp;
  }

  public String getDropObjectData() {
    return dropObjectData;
  }

  public void setDropObjectData(String dropObjectData) {
    this.dropObjectData = dropObjectData;
  }

  public String getAttrs() {
    return attrs;
  }

  public void setAttrs(String attrs) {
    this.attrs = attrs;
  }

  public List<Attribute> getAttributeList() {
    return attributeList;
  }

  public void setAttributeList(List<Attribute> attributeList) {
    this.attributeList = attributeList;
  }

  public List<Drop> getDropList() {
    return dropList;
  }

  public void setDropList(List<Drop> dropList) {
    this.dropList = dropList;
  }
}
