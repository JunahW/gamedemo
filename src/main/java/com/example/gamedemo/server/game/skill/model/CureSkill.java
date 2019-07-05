package com.example.gamedemo.server.game.skill.model;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.fight.progress.CureTargetHandler;

import java.util.List;

/**
 * @author wengj
 * @description:治疗技能
 * @date 2019/7/3
 */
public class CureSkill extends Skill {
  @Override
  public void useSkill(CreatureObject attacker, List<CreatureObject> targetList) {
    for (CreatureObject target : targetList) {
      CureTargetHandler.handle(attacker, target, this);
    }
  }
}
