package com.example.gamedemo.server.game.buff.constant;

/** buff类型 */
public enum BuffTypeEnum {
  /** 普通buff */
  COMmon_Buff(0, null),

  /** 攻击buff */
  Attack_buff(1, null);

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
