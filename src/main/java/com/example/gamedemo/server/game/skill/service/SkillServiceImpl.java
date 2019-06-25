package com.example.gamedemo.server.game.skill.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.model.Consume;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.monster.event.MonsterDeadEvent;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.skill.entity.SkillStorageEnt;
import com.example.gamedemo.server.game.skill.model.Skill;
import com.example.gamedemo.server.game.skill.resource.SkillResource;
import com.example.gamedemo.server.game.skill.storage.SkillStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    // 判断是否达到要求
    ItemStorage pack = player.getPack();
    List<Consume> consumeList = skillResource.getConsumeList();
    boolean isEnough = pack.checkPackItems(consumeList);
    if (!isEnough) {
      logger.info("道具不足，无法学习该技能");
      RequestException.throwException(I18nId.SKILL_NO_ENOUGH_ITEM);
    }
    /** 消耗道具 */
    pack.consumePackItems(consumeList);

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
  public boolean useSkill(Player player, int index, long targetId) {
    // 判断条件
    SkillStorage skillStorage = player.getSkillStorage();
    Skill skill = skillStorage.getSkillSlots()[index];
    if (skill == null) {
      logger.info("技能槽不存在该技能[{}]", index);
      RequestException.throwException(I18nId.SKILL_NO_EXIST);
    }
    // 判断魔法值是否满足要求
    long mp = player.getMp();
    SkillResource skillResource = skillManager.getSkillResourceById(skill.getSkillId());

    // 检查技能cd
    boolean checkSkillCd = checkSkillCd(skill, skillResource.getCd());
    if (!checkSkillCd) {
      logger.info("技能还未冷却");
      RequestException.throwException(I18nId.SKILL_NO_CD_YET);
    }

    if (mp < skillResource.getMp()) {
      logger.info("魔法值不足，无法使用技能[{}]", skill.getSkillId());
      RequestException.throwException(I18nId.MP_IS_NO_ENOUGH);
    }

    Scene scene = SpringContext.getSceneService().getSceneById(player.getSceneId());
    CreatureObject creatureObject = scene.getCreatureObjectById(targetId);
    if (creatureObject == null) {
      logger.info("该目标物不是Creature，不能被攻击");
      RequestException.throwException(I18nId.TARGET_NO_CREATURE);
    }

    skill.setLastUseTime(System.currentTimeMillis());
    // 减少mp
    player.setMp(mp - skillResource.getMp());
    Long attack = player.getPlayerAttributeContainer().getAttributeValue(AttributeTypeEnum.ATTACK);
    // 设置被攻击对象血量
    creatureObject.setHp(creatureObject.getHp() - attack);
    if (creatureObject.getHp() <= 0) {
      if (creatureObject instanceof Monster) {
        // 怪物死亡 触发事件
        EventBusManager.submitEvent(MonsterDeadEvent.valueOf(scene.getSceneResourceId(), targetId));
      }
    }
    return true;
  }

  /**
   * 检查技能cd
   *
   * @param skill
   * @param cd
   * @return
   */
  private boolean checkSkillCd(Skill skill, int cd) {
    long lastUseTime = skill.getLastUseTime();
    long currentTime = System.currentTimeMillis();
    if (currentTime >= lastUseTime + cd) {
      return true;
    }
    return false;
  }
}
