package com.example.gamedemo.server.game.equip.packet;

/**
 * @author wengj
 * @description：查看装备属性
 * @date 2019/5/30
 */
public class CM_GetEquipMsg {
  /** 物品id */
  private long guid;

  public long getGuid() {
    return guid;
  }
}
