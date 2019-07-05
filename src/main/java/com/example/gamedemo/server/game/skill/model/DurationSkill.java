package com.example.gamedemo.server.game.skill.model;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;

import java.util.Set;

/**
 * @author wengj
 * @description：持续性技能
 * @date 2019/7/4
 */
public class DurationSkill extends Skill {
  @Override
  public void useSkill(CreatureObject attacker, Set<CreatureObject> targetSet) {
    for (CreatureObject target : targetSet) {}
  }
}
