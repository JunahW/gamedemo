package com.example.gamedemo.server.common.model;

/**
 * @author wengj
 * @description:掉落物配置信息
 * @date 2019/7/4
 */
public class Drop {
  /** 物品id */
  private int itemId;

  /** 数量 */
  private int quantity;

  /** 概率 */
  private double chance;

  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getChance() {
    return chance;
  }

  public void setChance(double chance) {
    this.chance = chance;
  }
}
