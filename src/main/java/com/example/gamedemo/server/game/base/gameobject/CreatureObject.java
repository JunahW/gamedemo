package com.example.gamedemo.server.game.base.gameobject;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.utils.RandomUtils;
import com.example.gamedemo.server.game.attribute.AbstractAttributeContainer;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.base.model.SceneObjectView;
import com.example.gamedemo.server.game.buff.model.Buff;
import com.example.gamedemo.server.game.buff.model.BuffContainer;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;
import java.util.Set;

/**
 * @author wengj
 * @description：有生命的对象
 * @date 2019/6/21
 */
public abstract class CreatureObject<T extends CreatureObject> extends SceneObject {
  /** 视野 */
  @JsonIgnore private SceneObjectView sceneObjectView = new SceneObjectView();

  /** 血量 */
  private long hp;

  /** 魔法值 */
  private long mp;

  /** 经验值 */
  private long exp;

  /** Buff容器 */
  private BuffContainer<T> buffContainer = new BuffContainer<>();

  /** 属性容器 */
  private AbstractAttributeContainer attributeContainer;

  public SceneObjectView getSceneObjectView() {
    return sceneObjectView;
  }

  public void setSceneObjectView(SceneObjectView sceneObjectView) {
    this.sceneObjectView = sceneObjectView;
  }

  public long getHp() {
    return hp;
  }

  public void setHp(long hp) {
    this.hp = hp;
  }

  public long getMp() {
    return mp;
  }

  public void setMp(long mp) {
    this.mp = mp;
  }

  public long getExp() {
    return exp;
  }

  public void setExp(long exp) {
    this.exp = exp;
  }

  public BuffContainer<T> getBuffContainer() {
    return buffContainer;
  }

  public void setBuffContainer(BuffContainer<T> buffContainer) {
    this.buffContainer = buffContainer;
  }

  public AbstractAttributeContainer getAttributeContainer() {
    return attributeContainer;
  }

  public void setAttributeContainer(AbstractAttributeContainer attributeContainer) {
    this.attributeContainer = attributeContainer;
  }

  /**
   * 获取攻击力
   *
   * @return
   */
  public long getAttack() {
    Long attackLower = getAttributeContainer().getAttributeValue(AttributeTypeEnum.ATTACK_LOWER);
    Long attackUpper = getAttributeContainer().getAttributeValue(AttributeTypeEnum.ATTACK_UPPER);
    long attackValue = RandomUtils.getRandomNumBetween(attackLower, attackUpper);
    return attackValue;
  }

  /**
   * 获取防御力
   *
   * @return
   */
  public long getDefense() {
    Long defenseLower = getAttributeContainer().getAttributeValue(AttributeTypeEnum.DEFENSE_LOWER);
    Long defenseUpper = getAttributeContainer().getAttributeValue(AttributeTypeEnum.DEFENSE_UPPER);
    long defenseValue = RandomUtils.getRandomNumBetween(defenseLower, defenseUpper);
    return defenseValue;
  }

  /**
   * 新增buff
   *
   * @param buff
   */
  public void addBuff(Buff buff) {
    buffContainer.putBuff(buff);
  }

  /**
   * 移除buff
   *
   * @param buffId
   */
  public void removeBuff(int buffId) {
    buffContainer.removeBuff(buffId);
  }

  /** 执行buff */
  public void executeBuff() {
    BuffContainer<T> buffContainer = getBuffContainer();
    Set<Map.Entry<Integer, Buff>> entries = buffContainer.getBuffMap().entrySet();
    for (Map.Entry<Integer, Buff> entry : entries) {
      Buff buff = entry.getValue();
      BuffResource buffResource =
          SpringContext.getBuffService().getBuffResourceById(buff.getBuffId());
      // 加条件
      if (buff.canTrigger(buffResource.getPeriod())) {
        buff.setLastTriggerTime(buff.getLastTriggerTime() + buffResource.getPeriod());
        buff.active(this);
      }
    }
  }
}
