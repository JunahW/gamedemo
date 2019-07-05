package com.example.gamedemo.server.game.fight.progress;

import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.server.common.constant.GameConstant;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.monster.event.MonsterDeadEvent;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.skill.model.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengj
 * @description:击中目标处理器
 * @date 2019/7/4
 */
public class AttackTargetHandler {

  private static final Logger logger = LoggerFactory.getLogger(AttackTargetHandler.class);

  /**
   * @param attacker
   * @param target
   * @param skill
   */
  public static void handle(CreatureObject attacker, CreatureObject target, Skill skill) {
    // 计算攻击力
    long attack = attacker.getAttack();
    long defense = target.getDefense();

    long damage = getDamage(attack, defense);
    // 设置被攻击对象血量
    // 开始处理击流程
    target.setHp(target.getHp() - damage);
    logger.info("[{}]受到攻击,减少血量[{}]", target.getId(), damage);
    if (target.getHp() <= 0) {
      if (target instanceof Monster) {
        // 怪物死亡 触发事件
        EventBusManager.submitEvent(
            MonsterDeadEvent.valueOf(
                attacker,
                target.getSceneId(),
                target.getId(),
                ((Monster) target).getMonsterResourceId()));
      }
    }
  }

  /**
   * 计算伤害值
   *
   * @param attack
   * @param defense
   * @return
   */
  private static long getDamage(long attack, long defense) {
    long damage = 0;
    if (attack <= defense) {
      damage = GameConstant.MIN_ATTACK;
    } else {
      damage = attack - defense;
    }
    // TODO 默认值 暴击
    return damage;
  }
}
