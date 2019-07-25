package com.example.gamedemo.server.game.dungeon.packet;

/**
 * @author wengj
 * @description
 * @date 2019/7/25
 */
public class SM_FinshRount {
  /** 内容 */
  private String content;

  /** 回合 */
  private int round;

  public static SM_FinshRount valueOf(int round) {
    SM_FinshRount sm_finshRount = new SM_FinshRount();
    sm_finshRount.setContent("已完成回合");
    sm_finshRount.setRound(round);
    return sm_finshRount;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getRound() {
    return round;
  }

  public void setRound(int round) {
    this.round = round;
  }
}
