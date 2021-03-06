package com.example.gamedemo.server.game.bag.storage;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.model.Consume;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.List;

/**
 * @author wengj
 * @description 背包
 * @date 2019/5/21
 */
public class ItemStorage {

  /** 背包容量 */
  private Integer size = 50;

  /** 背包物品 */
  private AbstractItem[] abstractItems = new AbstractItem[50];

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public AbstractItem[] getAbstractItems() {
    return abstractItems;
  }

  public void setAbstractItems(AbstractItem[] abstractItems) {
    this.abstractItems = abstractItems;
  }

  /**
   * 通过guid获取道具
   *
   * @param guid
   * @return
   */
  public AbstractItem getStorageItemByObjectId(long guid) {
    for (int i = 0; i < abstractItems.length; i++) {
      if (abstractItems[i] != null) {
        if (abstractItems[i].getId() == guid) {
          return abstractItems[i];
        }
      }
    }
    return null;
  }

  /**
   * 通过guid减少道具数量
   *
   * @param guid
   * @param quantity
   */
  public boolean reduceStorageItemByObjectId(long guid, int quantity) {
    boolean isReduce = false;
    for (int i = 0; i < abstractItems.length; i++) {
      if (abstractItems[i] != null) {
        if (guid != abstractItems[i].getId()) {
          continue;
        }
        if (abstractItems[i].getQuantity() >= quantity) {
          int size = abstractItems[i].getQuantity() - quantity;
          if (size > 0) {
            abstractItems[i].setQuantity(size);
          } else {
            abstractItems[i] = null;
          }
          isReduce = true;
          break;
        }
      }
    }
    return isReduce;
  }

  /**
   * 通过itemResourceId减少道具数量
   *
   * @param itemResourceId
   * @param quantity
   */
  public boolean reduceStorageItemByItemResourceId(int itemResourceId, int quantity) {
    // 检查数量是否够消耗
    int storageQuantity = 0;
    for (int i = 0; i < abstractItems.length; i++) {
      if (abstractItems[i] != null) {
        if (abstractItems[i].getItemResourceId() == itemResourceId) {
          storageQuantity += abstractItems[i].getQuantity();
          if (storageQuantity >= quantity) {
            break;
          }
        }
      }
    }

    if (storageQuantity < quantity) {
      return false;
    }
    // 消耗道具
    for (int i = 0; i < abstractItems.length; i++) {
      if (abstractItems[i] != null) {
        if (abstractItems[i].getItemResourceId() == itemResourceId) {
          quantity -= abstractItems[i].getQuantity();
          if (quantity < 0) {
            abstractItems[i].setQuantity(-quantity);
            break;
          } else {
            abstractItems[i] = null;
          }
        }
      }
    }
    return true;
  }

  /**
   * 添加道具
   *
   * @param item
   * @return
   */
  public boolean addStorageItem(AbstractItem item) {
    int itemResourceId = item.getItemResourceId();
    ItemResource itemResource =
        SpringContext.getItemService().getItemResourceByItemResourceId(itemResourceId);
    boolean exist = isExist(item);
    // 物品存在
    if (exist) {
      for (AbstractItem abstractItem : abstractItems) {
        if (abstractItem != null) {
          // 可堆叠
          // 已经存在
          if (item.getItemResourceId() == abstractItem.getItemResourceId()) {
            // 判断是否超过堆叠上限
            if (abstractItem.getQuantity() + item.getQuantity() <= itemResource.getOverLimit()) {
              abstractItem.setQuantity(abstractItem.getQuantity() + item.getQuantity());
              item.setQuantity(0);
              break;
            } else {
              int add = itemResource.getOverLimit() - abstractItem.getQuantity();
              int rest = item.getQuantity() - add;

              abstractItem.setQuantity(abstractItem.getQuantity() + add);
              item.setQuantity(rest);
            }
          }
        }
      }
    }
    // 不可堆叠 堆叠达到上限 或者道具不存在
    if (!exist || item.getQuantity() > 0) {
      for (int i = 0; i < abstractItems.length; i++) {
        if (abstractItems[i] == null) {
          if (itemResource.getOverLimit() >= item.getQuantity()) {
            abstractItems[i] = item;
            break;
          } else {
            // 超过上限
            AbstractItem cloneItem = null;
            try {
              cloneItem = (AbstractItem) item.clone();
            } catch (CloneNotSupportedException e) {
              e.printStackTrace();
            }

            int rest = item.getQuantity() - itemResource.getOverLimit();
            cloneItem.setQuantity(itemResource.getOverLimit());
            abstractItems[i] = cloneItem;
            item.setQuantity(rest);
          }
        }
        if (i == abstractItems.length - 1 && item.getQuantity() > 0) {
          // 格子已满
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public String toString() {
    return "ItemStorage{"
        + "size="
        + size
        + ", abstractItems="
        + Arrays.toString(abstractItems)
        + '}';
  }

  /**
   * 判断物品是否存在于背包
   *
   * @param item
   * @return
   */
  private boolean isExist(AbstractItem item) {
    for (AbstractItem abstractItem : abstractItems) {
      if (abstractItem != null) {
        if (abstractItem.getItemResourceId() == item.getItemResourceId()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 检查背包的道具数量是否满足
   *
   * @param itemResourceId
   * @param quantity
   * @return
   */
  public boolean checkPackItemQuantity(int itemResourceId, int quantity) {
    int total = 0;
    for (AbstractItem item : abstractItems) {
      if (item != null) {
        if (item.getItemResourceId() == itemResourceId) {
          total += item.getQuantity();
          if (total >= quantity) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * 检查背包是够有足够道具消耗
   *
   * @param consumeList
   * @return
   */
  public boolean checkPackItems(List<Consume> consumeList) {
    boolean isEnough = true;
    for (Consume consume : consumeList) {
      // 消耗背包物品
      int itemId = consume.getItemId();
      int quantity = consume.getQuantity();
      // 检查背包是否满足条件
      isEnough = checkPackItemQuantity(itemId, quantity);
      if (!isEnough) {
        break;
      }
    }
    return isEnough;
  }

  /**
   * 消耗背包道具
   *
   * @param consumeList
   */
  public void consumePackItems(List<Consume> consumeList) {
    for (Consume consume : consumeList) {
      // 消耗背包物品
      int itemId = consume.getItemId();
      int quantity = consume.getQuantity();
      reduceStorageItemByItemResourceId(itemId, quantity);
    }
  }

  /**
   * 获取背包的空格数
   *
   * @return
   */
  @JsonIgnore
  public int getPackEmptySize() {
    int size = 0;
    for (AbstractItem item : abstractItems) {
      if (item == null) {
        size++;
      }
    }
    return size;
  }
}
