package com.example.gamedemo.server.game.dungeon.model;

import com.example.gamedemo.server.game.base.model.AbstractMapInfo;

/**
 * @author wengj
 * @description:经验副本信息
 * @date 2019/7/23
 */
public class DungeonMapInfo extends AbstractMapInfo {
  /** 上一次离开时间 */
  private long lastLeaveTime;

  /** 杀死怪物数量 */
  private int killMonsterQuantity;

  public long getLastLeaveTime() {
    return lastLeaveTime;
  }

  public void setLastLeaveTime(long lastLeaveTime) {
    this.lastLeaveTime = lastLeaveTime;
  }

  public int getKillMonsterQuantity() {
    return killMonsterQuantity;
  }

  public void setKillMonsterQuantity(int killMonsterQuantity) {
    this.killMonsterQuantity = killMonsterQuantity;
  }

  @Override
  public <T extends AbstractMapInfo> T valueOf() {
    DungeonMapInfo dungeonMapInfo = new DungeonMapInfo();
    return (T) dungeonMapInfo;
  }
}
