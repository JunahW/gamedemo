package com.example.gamedemo.server.game.equip.model;

import com.example.gamedemo.server.game.bag.model.EquipItem;

/**
 * @author wengj
 * @description:装备卡槽属性
 * @date 2019/6/5
 */
public class Slot {
  /** 卡槽的等级 */
  private int level;

  /** 卡槽的装备 */
  private EquipItem equipItem;

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public EquipItem getEquipItem() {
    return equipItem;
  }

  public void setEquipItem(EquipItem equipItem) {
    this.equipItem = equipItem;
  }

  @Override
  public String toString() {
    return "Slot{" + "level=" + level + ", equipItem=" + equipItem + '}';
  }
}
