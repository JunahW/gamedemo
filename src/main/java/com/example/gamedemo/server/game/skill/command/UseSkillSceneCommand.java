package com.example.gamedemo.server.game.skill.command;

import com.example.gamedemo.common.executer.scene.impl.AbstractSceneCommand;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.skill.model.Skill;

import java.util.Set;

/**
 * @author wengj
 * @description
 * @date 2019/7/16
 */
public class UseSkillSceneCommand extends AbstractSceneCommand {
  /** 攻击者 */
  private Player attacker;

  private Skill skill;

  /** 攻击目标 */
  private Set<CreatureObject> targetSet;

  public UseSkillSceneCommand(
      int sceneId, Player attacker, Skill skill, Set<CreatureObject> targetSet) {
    super(sceneId);
    this.attacker = attacker;
    this.skill = skill;
    this.targetSet = targetSet;
  }

  /**
   * @param sceneId
   * @param attacker
   * @param skill
   * @param targetSet
   * @return
   */
  public static UseSkillSceneCommand valueOf(
      int sceneId, Player attacker, Skill skill, Set<CreatureObject> targetSet) {
    UseSkillSceneCommand useSkillSceneCommand =
        new UseSkillSceneCommand(sceneId, attacker, skill, targetSet);
    return useSkillSceneCommand;
  }

  @Override
  public void doAction() {
    SpringContext.getFightService().doUseSkill(attacker, skill, targetSet);
  }
}
