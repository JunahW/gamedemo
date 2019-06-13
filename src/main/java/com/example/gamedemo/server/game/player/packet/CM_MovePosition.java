package com.example.gamedemo.server.game.player.packet;

/**
 * @author wengj
 * @description:移动场景信息
 * @date 2019/5/27
 */
public class CM_MovePosition {

  /** 横坐标 */
  private int x;

  /** 纵坐标 */
  private int y;

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
}
