package com.example.gamedemo.server.game.buff.model;

/**
 * @author wengj
 * @description：buff模型
 * @date 2019/6/25
 */
public class Buff {
  /** buffId; */
  private int buffId;

  /**
   * @param buffId
   * @return
   */
  public static Buff valueOf(int buffId) {
    Buff buff = new Buff();
    buff.setBuffId(buffId);
    return buff;
  }

  public int getBuffId() {
    return buffId;
  }

  public void setBuffId(int buffId) {
    this.buffId = buffId;
  }
}
