package com.example.gamedemo.server.game.npc.model;

import com.example.gamedemo.common.utils.UniqueIdUtils;

/**
 * @author wengj
 * @description：npc模型
 * @date 2019/6/19
 */
public class Npc {
  /** npcId */
  private long npcId;

  /** 配置表id */
  private int npcResourceId;

  /**
   * 获取NPC模型对象
   *
   * @param npcResourceId
   * @return
   */
  public static Npc valueOf(int npcResourceId) {
    Npc npc = new Npc();
    npc.setNpcId(UniqueIdUtils.nextId());
    npc.setNpcResourceId(npcResourceId);
    return npc;
  }

  public long getNpcId() {
    return npcId;
  }

  public void setNpcId(long npcId) {
    this.npcId = npcId;
  }

  public int getNpcResourceId() {
    return npcResourceId;
  }

  public void setNpcResourceId(int npcResourceId) {
    this.npcResourceId = npcResourceId;
  }
}
