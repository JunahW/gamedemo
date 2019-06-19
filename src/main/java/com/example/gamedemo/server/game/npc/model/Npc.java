package com.example.gamedemo.server.game.npc.model;

import com.example.gamedemo.common.utils.UniqueIdUtils;
import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;

/**
 * @author wengj
 * @description：npc模型
 * @date 2019/6/19
 */
public class Npc extends SceneObject {
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
    npc.setId(UniqueIdUtils.nextId());
    npc.setNpcResourceId(npcResourceId);
    return npc;
  }

  @Override
  public SceneObjectTypeEnum getSceneObjectType() {
    return SceneObjectTypeEnum.NPC;
  }

  public int getNpcResourceId() {
    return npcResourceId;
  }

  public void setNpcResourceId(int npcResourceId) {
    this.npcResourceId = npcResourceId;
  }
}
