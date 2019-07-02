package com.example.gamedemo.server.game.skill.command;

import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.common.executer.SceneCommand;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.monster.event.MonsterDeadEvent;
import com.example.gamedemo.server.game.monster.model.Monster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/7/1
 */
public class PeriodBuffCommand extends SceneCommand {
  private static final Logger logger = LoggerFactory.getLogger(PeriodBuffCommand.class);
  /** 目标对象 */
  private CreatureObject target;

  /** 影响的属性集 */
  private List<Attribute> effectList;

  public PeriodBuffCommand(int sceneId) {
    super(sceneId);
  }

  public static PeriodBuffCommand valueOf(
      int sceneId, CreatureObject target, List<Attribute> effectList) {
    PeriodBuffCommand command = new PeriodBuffCommand(sceneId);
    command.setTarget(target);
    command.setEffectList(effectList);
    return command;
  }

  @Override
  public void doAction() {
    logger.info("周期buff执行中");
    for (Attribute attribute : effectList) {
      if (AttributeTypeEnum.HP.equals(attribute.getType())) {
        long hp = target.getHp() + attribute.getValue();
        if (hp > target.getAttributeContainer().getAttributeValue(AttributeTypeEnum.HP)) {
          hp = target.getAttributeContainer().getAttributeValue(AttributeTypeEnum.HP);
        }
        target.setHp(hp);
        logger.info("[{}]血量受到的影响[{}]", target.getId(), attribute.getValue());
        if (target.getHp() <= 0) {
          if (target instanceof Monster) {
            // 怪物死亡 触发事件
            EventBusManager.submitEvent(
                MonsterDeadEvent.valueOf(target.getSceneId(), target.getId()));
          }
          cancel();
        }
      } else if (AttributeTypeEnum.MP.equals(attribute.getType())) {
        long mp = target.getMp() + attribute.getValue();
        if (mp > target.getAttributeContainer().getAttributeValue(AttributeTypeEnum.MP)) {
          mp = target.getAttributeContainer().getAttributeValue(AttributeTypeEnum.MP);
        }
        logger.info("[{}]魔法值受到的影响[{}]", target.getId(), attribute.getValue());
        target.setMp(mp);
      }
    }
  }

  public CreatureObject getTarget() {
    return target;
  }

  public void setTarget(CreatureObject target) {
    this.target = target;
  }

  public List<Attribute> getEffectList() {
    return effectList;
  }

  public void setEffectList(List<Attribute> effectList) {
    this.effectList = effectList;
  }
}
