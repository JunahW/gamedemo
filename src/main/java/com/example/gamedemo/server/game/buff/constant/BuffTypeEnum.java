package com.example.gamedemo.server.game.buff.constant;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.buff.model.AbstractBuff;
import com.example.gamedemo.server.game.buff.model.AttributeBuff;
import com.example.gamedemo.server.game.buff.model.CureBuff;
import com.example.gamedemo.server.game.buff.model.PoisonBuff;

/**
 * @author: wengj
 * @date: 2019/7/1
 * @description: buff类型
 */
public enum BuffTypeEnum {
  /** 治疗buff */
  CURE_BUFF(2, CureBuff.class),

  /** 修改属性的buff */
  ATTRIBUTE_BUFF(3, AttributeBuff.class) {},

  /** 中毒buff */
  POISON_BUFF(4, PoisonBuff.class);
  /** buff类型 */
  private int buffType;

  /** buff名称 */
  private Class<? extends AbstractBuff> buffClazz;

  BuffTypeEnum(int buffType, Class<? extends AbstractBuff> buffClazz) {
    this.buffType = buffType;
    this.buffClazz = buffClazz;
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

  public AbstractBuff createBuff(CreatureObject caster, int buffId, long endTime, long duration) {
    AbstractBuff buff = null;
    try {
      buff = this.getBuffClazz().newInstance();
      buff.setCaster(caster);
      buff.setBuffId(buffId);
      buff.setLastTriggerTime(System.currentTimeMillis());
      buff.setEndTime(endTime);
      buff.setDuration(duration);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return buff;
  }

  public int getBuffType() {
    return buffType;
  }

  public void setBuffType(int buffType) {
    this.buffType = buffType;
  }

  public Class<? extends AbstractBuff> getBuffClazz() {
    return buffClazz;
  }

  public void setBuffClazz(Class<? extends AbstractBuff> buffClazz) {
    this.buffClazz = buffClazz;
  }
}
