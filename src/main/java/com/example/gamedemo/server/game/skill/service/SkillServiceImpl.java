package com.example.gamedemo.server.game.skill.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.skill.entity.SkillStorageEnt;
import com.example.gamedemo.server.game.skill.model.Skill;
import com.example.gamedemo.server.game.skill.resource.SkillResource;
import com.example.gamedemo.server.game.skill.storage.SkillStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wengj
 * @description：技能业务
 * @date 2019/6/20
 */
@Service
public class SkillServiceImpl implements SkillService {

  private static final Logger logger = LoggerFactory.getLogger(SkillServiceImpl.class);

  @Autowired private SkillManager skillManager;

  @Override
  public SkillStorageEnt getSkillStorageEnt(Long playerId) {
    return skillManager.getSkillStorageEnt(playerId);
  }

  @Override
  public boolean studySkill(Player player, int skillId) {
    SkillResource skillResource = skillManager.getSkillResourceById(skillId);
    if (skillResource == null) {
      logger.info("该技能[{}]不存在", skillId);
      RequestException.throwException(I18nId.SKILL_NO_EXIST);
    }
    // TODO 判断是否达到要求
    Skill skill = Skill.valueOf(skillId);
    player.getSkillStorage().getSkills().put(skill.getSkillId(), skill);

    // 保存技能栏
    saveSkillStorage(player);
    return true;
  }

  @Override
  public boolean upgradeSkill(Player player, int skillId) {

    // 保存技能栏
    saveSkillStorage(player);
    return true;
  }

  @Override
  public boolean selectSkill(Player player, int skillId, int index) {
    SkillStorage skillStorage = player.getSkillStorage();
    Map<Integer, Skill> skills = skillStorage.getSkills();
    // 检查该技能是否已经学习
    if (!checkSkill(skills, skillId)) {
      logger.info("该技能[{}]还未学习", skillId);
      RequestException.throwException(I18nId.SKILL_NO_STUDY);
    }

    if (!checkIndex(index)) {
      logger.info("输入的技能栏位置[{}]有误", index);
      RequestException.throwException(I18nId.SKILL_INDEX_ERROR);
    }
    // 选择技能
    Skill[] skillSlots = skillStorage.getSkillSlots();
    skillSlots[index] = skills.get(skillId);
    // 保存技能栏
    saveSkillStorage(player);
    return true;
  }

  @Override
  public boolean removeSkill(Player player, int index) {
    SkillStorage skillStorage = player.getSkillStorage();
    if (!checkIndex(index)) {
      logger.info("输入的技能栏位置[{}]有误", index);
      RequestException.throwException(I18nId.SKILL_INDEX_ERROR);
    }
    Skill[] skillSlots = skillStorage.getSkillSlots();
    skillSlots[index] = null;

    // 保存技能栏
    saveSkillStorage(player);
    return true;
  }

  /**
   * 判断该技能是否已经学习
   *
   * @param skills
   * @param skillId
   * @return
   */
  private boolean checkSkill(Map<Integer, Skill> skills, Integer skillId) {
    return skills.containsKey(skillId);
  }

  /**
   * 检查输入的技能栏是否符合要求
   *
   * @param index
   * @return
   */
  private boolean checkIndex(int index) {
    if (index < 0 || index >= SystemConstant.SKILL_SLOT_SIZE) {
      return false;
    }
    return true;
  }

  @Override
  public void saveSkillStorage(Player player) {
    skillManager.saveSkillStorageEnt(getSkillStorageEnt(player.getId()));
  }

  @Override
  public void useSkill(Player player, int skillId, long targetId) {}
}
