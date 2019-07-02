package com.example.gamedemo.server.game.monster.packet;

/**
 * @author wengj
 * @description:获取怪物信息
 * @date 2019/7/2
 */
public class CM_GetMonster {
  /** 怪物id */
  private long monsterId;

  public long getMonsterId() {
    return monsterId;
  }
}
