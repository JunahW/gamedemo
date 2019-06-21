package com.example.gamedemo.server.game.monster.packet;

/**
 * @author wengj
 * @description
 * @date 2019/6/21
 */
public class CM_MonsterDead {
  /** 场景id */
  private int sceneId;

  /** 怪物id */
  private long monsterId;

  public int getSceneId() {
    return sceneId;
  }

  public long getMonsterId() {
    return monsterId;
  }
}
