package com.example.gamedemo.server.game.bag.model;

/**
 * @author wengj
 * @description：消耗实体
 * @date 2019/6/6
 */
public class Consume {

  /** 物品id */
  private int itemId;

  /** 数量 */
  private int quantity;

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
}
