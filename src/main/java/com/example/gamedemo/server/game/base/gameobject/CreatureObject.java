package com.example.gamedemo.server.game.base.gameobject;

import com.example.gamedemo.server.common.utils.RandomUtils;
import com.example.gamedemo.server.game.attribute.AbstractAttributeContainer;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.base.model.SceneObjectView;
import com.example.gamedemo.server.game.buff.model.BuffContainer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengj
 * @description：有生命的对象
 * @date 2019/6/21
 */
public abstract class CreatureObject<T extends CreatureObject> extends SceneObject {

  private static final Logger logger = LoggerFactory.getLogger(CreatureObject.class);
  /** 视野 */
  @JsonIgnore private SceneObjectView sceneObjectView = new SceneObjectView();

  /** 血量 */
  private long hp;

  /** 魔法值 */
  private long mp;

  /** 经验值 */
  private long exp;

  /** Buff容器 */
  private BuffContainer<T> buffContainer = new BuffContainer(this);

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
}
