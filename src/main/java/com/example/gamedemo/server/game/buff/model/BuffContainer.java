package com.example.gamedemo.server.game.buff.model;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
  private Map<Integer, AbstractBuff> buffMap = new ConcurrentHashMap<>();

  public BuffContainer() {}

  public BuffContainer(T owner) {
    this.owner = owner;
  }

  public T getOwner() {
    return owner;
  }

  public void setOwner(T owner) {
    this.owner = owner;
  }

  public Map<Integer, AbstractBuff> getBuffMap() {
    return buffMap;
  }

  public void setBuffMap(Map<Integer, AbstractBuff> buffMap) {
    this.buffMap = buffMap;
  }

  /**
   * 新增buff
   *
   * @param buff
   */
  public void putBuff(AbstractBuff buff) {
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
    AbstractBuff buff = buffMap.remove(buffId);
    if (buff != null) {
      buff.loseBuff(owner);
    }
    logger.info("[{}][{}]失去buff[{}]", owner.getSceneObjectType(), owner.getId(), buff.getBuffId());
  }

  public void addBuff(AbstractBuff buff) {
    AbstractBuff sameBuff = buffMap.get(buff.getBuffId());
    if (sameBuff != null) {
      if (buff.canMerge()) {
        merge(buff, sameBuff);
        return;
      } else {
        cover(buff, sameBuff);
        return;
      }
    }
    putBuff(buff);
  }

  /**
   * 合并buff
   *
   * @param buff
   * @param buffBeMerge
   */
  private void merge(AbstractBuff buff, AbstractBuff buffBeMerge) {
    if (buff.isMergeFull()) {
      BuffResource buffResource =
          SpringContext.getBuffService().getBuffResourceById(buff.getBuffId());
      logger.info("[{}][{}]叠加已经满了，无法继续叠加", buffResource.getBuffName(), buff.getBuffId());
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
  private void cover(AbstractBuff buff, AbstractBuff sameBuff) {
    removeBuff(buff.getBuffId());
    putBuff(sameBuff);
    logger.info("buff[{}]覆盖", buff.getBuffId());
  }

  /** 执行buff */
  public void executeBuff() {
    Set<Map.Entry<Integer, AbstractBuff>> entries = this.getBuffMap().entrySet();
    for (Map.Entry<Integer, AbstractBuff> entry : entries) {
      AbstractBuff buff = entry.getValue();
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

  /** 清理buff */
  public void clear() {
    buffMap.clear();
    logger.info("[{}][{}]清理buff", owner.getSceneObjectType(), owner.getId());
  }
}
