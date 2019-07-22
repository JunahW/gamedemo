package com.example.gamedemo.server.game.rank.constant;

/**
 * @author: wengj
 * @date: 2019/7/22
 * @description: 排名类型
 */
public enum RankTypeEnum {
  /** 战力 */
  BattleScore(0);
  private int index;

  RankTypeEnum(int index) {
    this.index = index;
  }

  /**
   * 通过下标获取
   *
   * @param index
   * @return
   */
  public static RankTypeEnum getRankTypeEnumByIndex(int index) {
    for (RankTypeEnum rankTypeEnum : RankTypeEnum.values()) {
      if (rankTypeEnum.getIndex() == index) {
        return rankTypeEnum;
      }
    }
    return null;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
