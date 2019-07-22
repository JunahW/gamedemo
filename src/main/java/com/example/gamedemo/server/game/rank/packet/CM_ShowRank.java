package com.example.gamedemo.server.game.rank.packet;

/**
 * @author wengj
 * @description：显示排行板
 * @date 2019/7/22
 */
public class CM_ShowRank {
  /** 下标 */
  private int index;

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
