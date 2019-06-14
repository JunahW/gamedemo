package com.example.gamedemo.server.game.player.packet;

/**
 * @author wengj
 * @description
 * @date 2019/6/14
 */
public class SM_PlayerWhere {
  private int sceneId;
  private int x;
  private int y;

  public static SM_PlayerWhere valueOf(int sceneId, int x, int y) {
    SM_PlayerWhere smPlayerWhere = new SM_PlayerWhere();
    smPlayerWhere.setSceneId(sceneId);
    smPlayerWhere.setX(x);
    smPlayerWhere.setY(y);
    return smPlayerWhere;
  }

  public int getSceneId() {
    return sceneId;
  }

  public void setSceneId(int sceneId) {
    this.sceneId = sceneId;
  }

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
