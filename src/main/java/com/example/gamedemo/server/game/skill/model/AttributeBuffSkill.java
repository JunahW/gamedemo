package com.example.gamedemo.server.game.skill.model;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.player.model.Player;

import java.util.Set;

/**
 * @author wengj
 * @description：持续性技能
 * @date 2019/7/4
 */
public class AttributeBuffSkill extends Skill {
  @Override
  public void useSkill(Player attacker, Set<CreatureObject> targetSet) {}
}
