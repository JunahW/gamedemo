package com.example.gamedemo.server.game.skill.model;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.fight.progress.CureTargetHandler;

import java.util.Set;

/**
 * @author wengj
 * @description:治疗技能
 * @date 2019/7/3
 */
public class CureSkill extends Skill {
  @Override
  public void useSkill(CreatureObject attacker, Set<CreatureObject> targetSet) {
    for (CreatureObject target : targetSet) {
      CureTargetHandler.handle(attacker, target, this);
    }
  }
}
