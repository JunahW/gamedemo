package com.example.gamedemo.server.game.buff.constant;

/**
 * @author: wengj
 * @date: 2019/7/1
 * @description: buff类型
 */
public enum BuffTypeEnum {
  /** 眩晕buff */
  DURATION_BUFF(0, null),

  /** 攻击buff */
  ATTACK_BUFF(1, null),

  /** 周期buff */
  PERRIOD_BUFF(2, null);

  /** buff类型 */
  private int buffType;

  /** buff名称 */
  private Class buffClazz;

  BuffTypeEnum(int buffType, Class buffClazz) {
    this.buffType = buffType;
    this.buffClazz = buffClazz;
  }

  public int getBuffType() {
    return buffType;
  }

  public void setBuffType(int buffType) {
    this.buffType = buffType;
  }

  public Class getBuffClazz() {
    return buffClazz;
  }

  public void setBuffClazz(Class buffClazz) {
    this.buffClazz = buffClazz;
  }
}
