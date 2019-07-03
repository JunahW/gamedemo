package com.example.gamedemo.server.game.skill.model;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;

import java.util.List;

/**
 * @author wengj
 * @description：攻击性技能
 * @date 2019/7/3
 */
public class AttackSkill extends Skill {
  @Override
  public void useSkill(CreatureObject attacker, List<CreatureObject> targetList) {
    System.out.println("使用攻击技能");
  }
}
