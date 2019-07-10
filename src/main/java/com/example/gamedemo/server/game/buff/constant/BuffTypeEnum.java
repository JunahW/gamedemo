package com.example.gamedemo.server.game.buff.constant;

import com.example.gamedemo.server.game.attribute.constant.AttributeModelId;
import com.example.gamedemo.server.game.buff.model.AttributeBuff;
import com.example.gamedemo.server.game.buff.model.Buff;
import com.example.gamedemo.server.game.buff.model.CureBuff;

/**
 * @author: wengj
 * @date: 2019/7/1
 * @description: buff类型
 */
public enum BuffTypeEnum implements AttributeModelId {
  /** 眩晕buff */
  DURATION_BUFF(0, CureBuff.class),

  /** 攻击buff */
  ATTACK_BUFF(1, CureBuff.class),

  /** 周期buff */
  PERIOD_BUFF(2, CureBuff.class),

  /** 修改属性的buff */
  ATTRIBUTE_BUFF(3, AttributeBuff.class) {
    @Override
    public String getModelName() {
      return "ATTRIBUTE_BUFF";
    }
  };

  /** buff类型 */
  private int buffType;

  /** buff名称 */
  private Class<? extends Buff> buffClazz;

  BuffTypeEnum(int buffType, Class<? extends Buff> buffClazz) {
    this.buffType = buffType;
    this.buffClazz = buffClazz;
  }

  public static Buff createBuff(int buffType, int buffId, long endTime) {
    Buff buff = null;
    for (BuffTypeEnum buffTypeEnum : BuffTypeEnum.values()) {
      if (buffTypeEnum.getBuffType() == buffType) {
        try {
          buff = buffTypeEnum.getBuffClazz().newInstance();
          buff.setBuffId(buffId);
          buff.setLastTriggerTime(System.currentTimeMillis());
          buff.setEndTime(endTime);
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    return buff;
  }

  /**
   * 通过buffType获取BuffTypeEnum
   *
   * @param buffType
   * @return
   */
  public static BuffTypeEnum getBuffTypeEnumByBuffType(int buffType) {
    for (BuffTypeEnum buffTypeEnum : BuffTypeEnum.values()) {
      if (buffTypeEnum.getBuffType() == buffType) {
        return buffTypeEnum;
      }
    }
    return null;
  }

  public int getBuffType() {
    return buffType;
  }

  public void setBuffType(int buffType) {
    this.buffType = buffType;
  }

  public Class<? extends Buff> getBuffClazz() {
    return buffClazz;
  }

  public void setBuffClazz(Class<? extends Buff> buffClazz) {
    this.buffClazz = buffClazz;
  }

  @Override
  public String getModelName() {
    return null;
  }
}
