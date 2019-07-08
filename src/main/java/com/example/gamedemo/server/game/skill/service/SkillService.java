package com.example.gamedemo.server.game.skill.service;

import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.skill.entity.SkillStorageEnt;
import com.example.gamedemo.server.game.skill.resource.SkillResource;

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
  SkillStorageEnt getSkillStorageEnt(Long playerId);

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

  /**
   * 选择技能
   *
   * @param player
   * @param skillId
   * @param index
   * @return
   */
  boolean selectSkill(Player player, int skillId, int index);

  /**
   * 移除技能
   *
   * @param player
   * @param index
   * @return
   */
  boolean removeSkill(Player player, int index);

  /**
   * 保存技能栏
   *
   * @param player
   */
  void saveSkillStorage(Player player);

  /**
   * 获取配置信息
   *
   * @param id
   * @return
   */
  SkillResource getSkillResourceById(int id);
}
