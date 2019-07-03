package com.example.gamedemo.server.game.skill.model;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;

import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/6/25
 */
public class BuffSkill extends Skill {
  @Override
  public void useSkill(CreatureObject attacker, List<CreatureObject> targetList) {
    System.out.println("使用buff技能");
  }
}
