package com.example.gamedemo.server.game.equip.constant;

import com.example.gamedemo.server.game.attribute.constant.AttributeModelId;

/**
 * @author: wengj
 * @date: 2019/5/30
 * @description: 装备类型
 */
public enum EquipmentType implements AttributeModelId {
  /** 武器 */
  WEAPON(0) {
    @Override
    public String getModelName() {
      return "WEAPON";
    }
  },

  /** 衣服 */
  CLOTH(1) {
    @Override
    public String getModelName() {
      return "CLOTH";
    }
  },

  /** 头盔 */
  HAT(2) {
    @Override
    public String getModelName() {
      return "HAT";
    }
  },

  /** 战靴 */
  SHOE(3) {
    @Override
    public String getModelName() {
      return "SHOE";
    }
  };

  /** 装备类型 */
  private int equipmentType;

  EquipmentType(int equipmentType) {
    this.equipmentType = equipmentType;
  }

  /**
   * 获取装备类型
   *
   * @param id
   * @return
   */
  public static EquipmentType getEquipmentTypeId(int id) {
    for (EquipmentType equipmentType : EquipmentType.values()) {
      if (equipmentType.equipmentType == id) {
        return equipmentType;
      }
    }
    return null;
  }

  public int getEquipmentType() {
    return equipmentType;
  }

  public void setEquipmentType(int equipmentType) {
    this.equipmentType = equipmentType;
  }
}
