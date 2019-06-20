package com.example.gamedemo.server.game.skill.service;

import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.skill.storage.SkillStorage;

/**
 * @author: wengj
 * @date: 2019/6/20
 * @description: 技能业务接口
 */
public interface SkillService {

  /**
   * 获取技能栏
   *
   * @param playerId
   * @return
   */
  SkillStorage getSkillStorage(Long playerId);

  /**
   * 学习技能
   *
   * @param player
   * @param skillId
   * @return
   */
  boolean studySkill(Player player, int skillId);

  /**
   * 升级技能
   *
   * @param player
   * @param skillId
   * @return
   */
  boolean upgradeSkill(Player player, int skillId);
}
