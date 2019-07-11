package com.example.gamedemo.server.game.buff.model;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    logger.info(
        "[{}][{}]新增buff[{}]",
        getOwner().getSceneObjectType(),
        getOwner().getId(),
        buff.getBuffId());
    buffMap.put(buff.getBuffId(), buff);
    buff.gainBuff(owner);
  }

  /**
   * 移除buff
   *
   * @param buffId
   */
  public void removeBuff(Integer buffId) {
    Buff buff = buffMap.remove(buffId);
    if (buff != null) {
      buff.loseBuff(owner);
    }
  }

  public void addBuff(Buff buff) {
    Buff sameBuff = buffMap.get(buff.getBuffId());
    if (sameBuff != null) {
      if (buff.canMerge()) {
        merge(buff, sameBuff);
      } else {
        cover(buff, sameBuff);
      }
    } else {

    }
    putBuff(buff);
  }

  /**
   * 合并buff
   *
   * @param buff
   * @param buffBeMerge
   */
  private void merge(Buff buff, Buff buffBeMerge) {
    if (buff.isMergeFull()) {
      BuffResource buffResource =
          SpringContext.getBuffService().getBuffResourceById(buff.getBuffId());
      logger.info("[{}][{}]叠加已经拿了，无法继续叠加", buffResource.getBuffName(), buff.getBuffId());
      return;
    }
    buff.merge(buffBeMerge);
  }

  /**
   * 合并buff
   *
   * @param buff
   * @param sameBuff
   */
  private void cover(Buff buff, Buff sameBuff) {
    removeBuff(buff.getBuffId());
    putBuff(sameBuff);
    logger.info("buff[{}]覆盖", buff.getBuffId());
  }

  /** 执行buff */
  public void executeBuff() {
    Set<Map.Entry<Integer, Buff>> entries = this.getBuffMap().entrySet();
    for (Map.Entry<Integer, Buff> entry : entries) {
      Buff buff = entry.getValue();
      BuffResource buffResource =
          SpringContext.getBuffService().getBuffResourceById(buff.getBuffId());
      // buff是否已经结束，结束则移除
      if (buff.isEnd()) {
        this.removeBuff(buff.getBuffId());
        continue;
      }
      if (buff.canTrigger(buffResource.getPeriod())) {
        buff.setLastTriggerTime(buff.getLastTriggerTime() + buffResource.getPeriod());
        buff.active(getOwner());
      }
    }
  }
}
