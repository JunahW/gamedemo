package com.example.gamedemo.server.game.skill.model;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.fight.progress.AttackTargetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author wengj
 * @description：攻击性技能
 * @date 2019/7/3
 */
public class AttackSkill extends Skill {

  private static final Logger logger = LoggerFactory.getLogger(AttackSkill.class);

  @Override
  public void useSkill(CreatureObject attacker, Set<CreatureObject> targetSet) {
    for (CreatureObject target : targetSet) {
      // 处理攻击
      AttackTargetHandler.handle(attacker, target, this);
    }
  }
}
