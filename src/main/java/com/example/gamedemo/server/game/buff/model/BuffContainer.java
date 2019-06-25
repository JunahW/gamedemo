package com.example.gamedemo.server.game.buff.model;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description：Buff容器
 * @date 2019/6/25
 */
public class BuffContainer<T extends CreatureObject> {
  /** buff拥有者 */
  private T owner;

  /** buffer集合 */
  private Map<Integer, Buff> buffMap = new HashMap<>();

  public T getOwner() {
    return owner;
  }

  public void setOwner(T owner) {
    this.owner = owner;
  }

  public Map<Integer, Buff> getBuffMap() {
    return buffMap;
  }

  public void setBuffMap(Map<Integer, Buff> buffMap) {
    this.buffMap = buffMap;
  }

  /**
   * 新增buff
   *
   * @param buff
   */
  public void putBuff(Buff buff) {
    buffMap.put(buff.getBuffId(), buff);
  }

  /**
   * 移除buff
   *
   * @param buffId
   */
  public void removeBuff(Integer buffId) {
    buffMap.remove(buffId);
  }
}
