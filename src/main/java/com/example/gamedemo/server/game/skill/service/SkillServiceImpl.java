package com.example.gamedemo.server.game.skill.service;

import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.skill.storage.SkillStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wengj
 * @description：技能业务
 * @date 2019/6/20
 */
@Service
public class SkillServiceImpl implements SkillService {
  @Autowired private SkillManager skillManager;

  @Override
  public SkillStorage getSkillStorage(Long playerId) {
    return skillManager.getSkillStorageEnt(playerId).getSkillStorage();
  }

  @Override
  public boolean studySkill(Player player, int skillId) {
    return false;
  }

  @Override
  public boolean upgradeSkill(Player player, int skillId) {
    return false;
  }
}
