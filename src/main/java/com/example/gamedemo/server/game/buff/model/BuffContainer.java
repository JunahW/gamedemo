package com.example.gamedemo.server.game.buff.model;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.buff.constant.BuffTypeEnum;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description：Buff容器
 * @date 2019/6/25
 */
public class BuffContainer<T extends CreatureObject> {
  private static final Logger logger = LoggerFactory.getLogger(BuffContainer.class);
  /** buff拥有者 */
  @JsonIgnore private T owner;

  /** buffer集合 */
  private Map<Integer, Buff> buffMap = new HashMap<>();

  public BuffContainer(T owner) {
    this.owner = owner;
  }

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
    buff.gainBuff(owner);
  }

  /**
   * 移除buff
   *
   * @param buffId
   */
  public void removeBuff(Integer buffId) {
    buffMap.get(buffId).loseBuff(owner);
    buffMap.remove(buffId);
  }

  /**
   * 通过buffId数组新增buff
   *
   * @param buffArray
   */
  public void addBuffsByBuffIdArray(int[] buffArray) {
    long currentTimeMillis = System.currentTimeMillis();
    for (int buffId : buffArray) {
      BuffResource buffResource = SpringContext.getBuffService().getBuffResourceById(buffId);
      Buff buff =
          BuffTypeEnum.createBuff(
              buffResource.getBuffType(), buffId, currentTimeMillis + buffResource.getDuration());
      addBuff(buff);
    }
  }

  public void addBuff(Buff buff) {
    logger.info(
        "[{}][{}]新增buff[{}]",
        getOwner().getSceneObjectType(),
        getOwner().getId(),
        buff.getBuffId());
    putBuff(buff);
  }
}
