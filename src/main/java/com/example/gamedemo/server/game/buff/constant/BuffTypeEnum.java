package com.example.gamedemo.server.game.buff.constant;

import com.example.gamedemo.server.game.buff.model.Buff;

/**
 * @author: wengj
 * @date: 2019/7/1
 * @description: buff类型
 */
public enum BuffTypeEnum {
  /** 眩晕buff */
  DURATION_BUFF(0, Buff.class),

  /** 攻击buff */
  ATTACK_BUFF(1, Buff.class),

  /** 周期buff */
  PERIOD_BUFF(2, Buff.class);

  /** buff类型 */
  private int buffType;

  /** buff名称 */
  private Class<? extends Buff> buffClazz;

  BuffTypeEnum(int buffType, Class<? extends Buff> buffClazz) {
    this.buffType = buffType;
    this.buffClazz = buffClazz;
  }

  public static Buff createBuff(int buffType, int buffId) {
    Buff buff = null;
    for (BuffTypeEnum buffTypeEnum : BuffTypeEnum.values()) {
      if (buffTypeEnum.getBuffType() == buffType) {
        try {
          buff = buffTypeEnum.getBuffClazz().newInstance();
          buff.setBuffId(buffId);
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    return buff;
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
}
