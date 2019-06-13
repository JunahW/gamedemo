package com.example.gamedemo.server.game.bag.constant;

import com.example.gamedemo.server.game.bag.model.*;

/**
 * @author: wengj
 * @date: 2019/5/29
 * @description: 道具类型
 */
public enum ItemType {

  /** 背包物品 */
  Common(0, CommonItem.class),

  Equip(1, EquipItem.class),

  Gemstone(2, GemstoneItem.class),

  Medicine(3, MedicineItem.class),
  ;

  /** 道具类型id */
  private int id;
  /** 道具实例的class */
  private Class<? extends AbstractItem> itemClazz;

  ItemType(int id, Class bagItemClazz) {
    this.id = id;
    this.itemClazz = bagItemClazz;
  }

  /**
   * 创建背包道具
   *
   * @return
   */
  public static AbstractItem create(int id) {

    ItemType[] values = values();
    AbstractItem abstractItem = null;
    for (ItemType itemType : values) {
      if (itemType.getId() == id) {
        try {
          abstractItem = itemType.getItemClazz().newInstance();
          break;
        } catch (Exception e) {
          e.printStackTrace();
          throw new IllegalArgumentException("生成道具失败" + itemType.getItemClazz().getName());
        }
      }
    }
    return abstractItem;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Class<? extends AbstractItem> getItemClazz() {
    return itemClazz;
  }

  public void setItemClazz(Class<? extends AbstractItem> itemClazz) {
    this.itemClazz = itemClazz;
  }
}
