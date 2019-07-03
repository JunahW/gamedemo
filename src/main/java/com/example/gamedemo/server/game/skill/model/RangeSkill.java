package com.example.gamedemo.server.game.skill.model;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;

import java.util.List;

/**
 * @author wengj
 * @description：范围技能
 * @date 2019/6/20
 */
public class RangeSkill extends Skill {
  @Override
  public void useSkill(CreatureObject attacker, List<CreatureObject> targetList) {
    System.out.println("使用范围技能");
  }
}
