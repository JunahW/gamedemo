package com.example.gamedemo.server.game.base.resource.bean;

import java.util.List;

/**
 * @author wengj
 * @description：回合
 * @date 2019/7/24
 */
public class CheckPointDef {

  /** 回合 */
  private int round;

  /** 怪物id */
  private int monsterId;

  /** 数量 */
  private int quantity;
  /** 奖励 */
  private List<RewardDef> rewardDefs;

  public int getRound() {
    return round;
  }

  public void setRound(int round) {
    this.round = round;
  }

  public int getMonsterId() {
    return monsterId;
  }

  public void setMonsterId(int monsterId) {
    this.monsterId = monsterId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public List<RewardDef> getRewardDefs() {
    return rewardDefs;
  }

  public void setRewardDefs(List<RewardDef> rewardDefs) {
    this.rewardDefs = rewardDefs;
  }
}
