package com.example.gamedemo.server.game.bag.packet;

/**
 * @author wengj
 * @description
 * @date 2019/6/11
 */
public class SM_GetBagCapacity {
  private int capacity;

  public SM_GetBagCapacity(int capacity) {
    this.capacity = capacity;
  }

  public static SM_GetBagCapacity valueOf(int capacity) {
    return new SM_GetBagCapacity(capacity);
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }
}
