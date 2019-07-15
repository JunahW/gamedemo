package com.example.gamedemo.server.game.dungeon.packet;

/**
 * @author wengj
 * @description：副本倒计时信息
 * @date 2019/7/15
 */
public class SM_DungeonCountDown {
  /** 倒计时 */
  private int countDown;

  public static SM_DungeonCountDown valueOf(int countDown) {
    SM_DungeonCountDown sm_dungeonCountDown = new SM_DungeonCountDown();
    sm_dungeonCountDown.setCountDown(countDown);
    return sm_dungeonCountDown;
  }

  public int getCountDown() {
    return countDown;
  }

  public void setCountDown(int countDown) {
    this.countDown = countDown;
  }
}
