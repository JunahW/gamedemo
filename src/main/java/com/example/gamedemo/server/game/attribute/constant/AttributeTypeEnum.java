package com.example.gamedemo.server.game.attribute.constant;

import com.example.gamedemo.server.game.attribute.computer.IAttributeComputer;
import com.example.gamedemo.server.game.attribute.computer.impl.DefaultAttributeComputer;
import com.example.gamedemo.server.game.attribute.computer.impl.GeneralAttributeComputer;

/**
 * @author: wengj
 * @date: 2019/6/3
 * @description:属性类型
 */
public enum AttributeTypeEnum {
  /** 生命值 */
  HP("HP", "生命值", false),

  /** 魔法值 */
  MP("MP", "魔法值", false),

  /** 攻击力 */
  ATTACK("ATTACK", "攻击力", false) {
    @Override
    public AttributeTypeEnum[] getPercentageAttributes() {
      return new AttributeTypeEnum[] {ATTACK_PERCENTAGE};
    }
  },

  /** 防御力 */
  DEFENSE("DEFENSE", "防御力", false) {
    @Override
    public AttributeTypeEnum[] getPercentageAttributes() {
      return new AttributeTypeEnum[] {DEFENSE_PERCENTAGE};
    }
  },

  /** 攻击力下限 */
  ATTACK_LOWER("ATTACK_LOWER", "攻击力下限", false) {
    @Override
    public AttributeTypeEnum[] getPercentageAttributes() {
      return new AttributeTypeEnum[] {ATTACK_PERCENTAGE};
    }

    @Override
    public IAttributeComputer getAttributeComputer() {
      return new GeneralAttributeComputer();
    }
  },

  /** 攻击力上限 */
  ATTACK_UPPER("ATTACK_UPPER", "攻击力上限", false) {
    @Override
    public AttributeTypeEnum[] getPercentageAttributes() {
      return new AttributeTypeEnum[] {ATTACK_PERCENTAGE};
    }

    @Override
    public IAttributeComputer getAttributeComputer() {
      return new GeneralAttributeComputer();
    }
  },

  /** 防御力下限 */
  DEFENSE_LOWER("DEFENSE_LOWER", "防御力下限", false) {
    @Override
    public AttributeTypeEnum[] getPercentageAttributes() {
      return new AttributeTypeEnum[] {DEFENSE_PERCENTAGE};
    }

    @Override
    public IAttributeComputer getAttributeComputer() {
      return new GeneralAttributeComputer();
    }
  },

  /** 防御力上限 */
  DEFENSE_UPPER("DEFENSE_UPPER", "防御力上限", false) {
    @Override
    public AttributeTypeEnum[] getPercentageAttributes() {
      return new AttributeTypeEnum[] {DEFENSE_PERCENTAGE};
    }

    @Override
    public IAttributeComputer getAttributeComputer() {
      return new GeneralAttributeComputer();
    }
  },

  /** 攻击加成 */
  ATTACK_PERCENTAGE("", "攻击加成", true),

  /** 防御加成 */
  DEFENSE_PERCENTAGE("", "防御加成", true);

  /** 属性id */
  private String name;
  /** 属性描述 */
  private String description;

  private boolean isPercentage;

  AttributeTypeEnum(String name, String description, boolean isPercentage) {
    this.name = name;
    this.description = description;
    this.isPercentage = isPercentage;
  }

  /**
   * 通过name获取对应的枚举
   *
   * @param type
   * @return
   */
  public static AttributeTypeEnum getAttributeTypeEnumByType(String type) {
    for (AttributeTypeEnum attributeTypeEnum : AttributeTypeEnum.values()) {
      if (attributeTypeEnum.getName().equals(type)) {
        return attributeTypeEnum;
      }
    }
    return null;
  }

  /**
   * 获取属性所受到影响的百分比属性
   *
   * @return
   */
  public AttributeTypeEnum[] getPercentageAttributes() {
    return new AttributeTypeEnum[] {};
  }

  public IAttributeComputer getAttributeComputer() {
    return new DefaultAttributeComputer();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isPercentage() {
    return isPercentage;
  }

  public void setPercentage(boolean percentage) {
    isPercentage = percentage;
  }
}
