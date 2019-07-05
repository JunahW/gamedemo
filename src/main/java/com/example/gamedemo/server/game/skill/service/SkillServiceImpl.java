package com.example.gamedemo.server.game.skill.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.executer.scene.SceneExecutor;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.model.Consume;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;
import com.example.gamedemo.server.game.base.model.SceneObjectView;
import com.example.gamedemo.server.game.buff.command.RemoveBuffCommand;
import com.example.gamedemo.server.game.buff.constant.BuffTypeEnum;
import com.example.gamedemo.server.game.buff.constant.TargetType;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import com.example.gamedemo.server.game.monster.event.MonsterDeadEvent;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.skill.command.PeriodBuffCommand;
import com.example.gamedemo.server.game.skill.constant.SkillTypeEnum;
import com.example.gamedemo.server.game.skill.entity.SkillStorageEnt;
import com.example.gamedemo.server.game.skill.model.Skill;
import com.example.gamedemo.server.game.skill.resource.SkillResource;
import com.example.gamedemo.server.game.skill.storage.SkillStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    Skill oldSkill = player.getSkillStorage().getSkills().get(skillId);
    if (oldSkill != null) {
      logger.info("[{}]该技能已经学习", skillId);
      RequestException.throwException(I18nId.SKILL_STUDIED);
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

    Skill skill = SkillTypeEnum.createSkill(skillResource.getSkillType(), skillId);

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
    Integer[] skillSlots = skillStorage.getSkillSlots();
    skillSlots[index] = skillId;
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
    Integer[] skillSlots = skillStorage.getSkillSlots();
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
  public boolean useSkill(Player player, int index) {
    // 判断条件
    SkillStorage skillStorage = player.getSkillStorage();
    Skill skill = skillStorage.getSkillByIndex(index);
    if (skill == null) {
      logger.info("技能槽不存在该技能[{}]", index);
      RequestException.throwException(I18nId.SKILL_NO_EXIST);
    }
    // 判断魔法值是否满足要求
    long mp = player.getMp();
    SkillResource skillResource = skillManager.getSkillResourceById(skill.getSkillId());

    // 检查技能cd
    boolean checkSkillCd = player.getCdComponent().isSkillInCd(skill.getSkillId());
    if (!checkSkillCd) {
      logger.info("技能还未冷却");
      RequestException.throwException(I18nId.SKILL_NO_CD_YET);
    }

    if (mp < skillResource.getMp()) {
      logger.info("魔法值不足，无法使用技能[{}]", skill.getSkillId());
      RequestException.throwException(I18nId.MP_IS_NO_ENOUGH);
    }

    /*  */
    /** 普通攻击 */
    /*
    if (SkillTypeEnum.COMMON_SKILL.getId() == skillResource.getSkillType()) {
      CreatureObject attackTarget = getAttackTarget(player, skillResource);
      if (attackTarget == null) {
        logger.info("附近不存在攻击对象");
        SessionManager.sendMessage(player, SM_NoticeMessge.valueOf("附近不存在攻击对象"));
        return true;
      }
      useCommonSkill(player, skill, attackTarget);
    }
    */
    /** 范围攻击 */
    /*
    if (SkillTypeEnum.RANGE_SKILL.getId() == skillResource.getSkillType()) {
      useRangeSkill(player, skill);
    }

    */
    /** buff技能 */
    /*
    if (SkillTypeEnum.BUFF_SKILL.getId() == skillResource.getSkillType()) {
      useBuffSkill(player, skill);
    }*/
    return true;
  }

  @Override
  public SkillResource getSkillResourceById(int id) {
    return skillManager.getSkillResourceById(id);
  }

  /**
   * 范围攻击
   *
   * @param player
   * @param skill
   */
  private void useRangeSkill(Player player, Skill skill) {

    SkillResource skillResource = skillManager.getSkillResourceById(skill.getSkillId());
    // 设置cd时间
    player
        .getCdComponent()
        .setSkillCd(skill.getSkillId(), System.currentTimeMillis() + skillResource.getCd());
    // 减少mp
    player.setMp(player.getMp() - skillResource.getMp());

    List<CreatureObject> attackTargetList = getAttackTargetList(player, skillResource);
    Long attack =
        player.getAttributeContainer().getAttributeValue(AttributeTypeEnum.ATTACK)
            + skillResource.getAttack();
    for (CreatureObject creatureObject : attackTargetList) {
      // 设置被攻击对象血量
      logger.info("[{}]受到攻击，消耗血量[{}]", creatureObject.getId(), attack);
      creatureObject.setHp(creatureObject.getHp() - attack);
      if (creatureObject.getHp() <= 0) {
        if (creatureObject instanceof Monster) {
          // 怪物死亡 触发事件
          EventBusManager.submitEvent(
              MonsterDeadEvent.valueOf(
                  player,
                  creatureObject.getSceneId(),
                  creatureObject.getId(),
                  ((Monster) creatureObject).getMonsterResourceId()));
        }
      }
    }
  }

  /**
   * 使用buff技能
   *
   * @param player
   * @param skill
   */
  private void useBuffSkill(Player player, Skill skill) {
    logger.info("[{}]使用了buff技能[{}]", player.getId(), skill.getSkillId());
    int skillId = skill.getSkillId();
    SkillResource skillResource = skillManager.getSkillResourceById(skillId);

    player
        .getCdComponent()
        .setSkillCd(skill.getSkillId(), System.currentTimeMillis() + skillResource.getCd());
    // 减少mp
    player.setMp(player.getMp() - skillResource.getMp());

    /** 添加buff */
    int[] buffArray = skillResource.getBuffArray();
    for (int buffId : buffArray) {
      BuffResource buffResource = SpringContext.getBuffService().getBuffResourceById(buffId);
      int duration = buffResource.getDuration();
      CreatureObject creatureObject;
      if (TargetType.SELF == buffResource.getTarget()) {
        creatureObject = player;
      } else {
        creatureObject = getAttackTarget(player, skillResource);
        if (creatureObject == null) {
          logger.info("附近不存在攻击对象");
          SessionManager.sendMessage(player, SM_NoticeMessge.valueOf("附近不存在攻击对象"));
          return;
        }
      }
      SpringContext.getBuffService()
          .addBuff(creatureObject, BuffTypeEnum.createBuff(buffResource.getBuffType(), buffId));
      if (BuffTypeEnum.DURATION_BUFF.getBuffType() == buffResource.getBuffType()) {
        // 提交buff移除任务
      } else if (BuffTypeEnum.PERIOD_BUFF.getBuffType() == buffResource.getBuffType()) {
        long endTime = System.currentTimeMillis() + duration;
        PeriodBuffCommand command =
            PeriodBuffCommand.valueOf(
                creatureObject.getSceneId(), player, creatureObject, buffResource.getEffectList());
        SceneExecutor.addScheduleTask(
            creatureObject.getSceneId(), 0, buffResource.getPeriod(), endTime, command);

      } else if (BuffTypeEnum.ATTACK_BUFF.getBuffType() == buffResource.getBuffType()) {
        // TODO
      }
      /** 移除buff */
      SceneExecutor.addDelayTask(
          RemoveBuffCommand.valueOf(
              creatureObject.getSceneId(), creatureObject.getBuffContainer(), buffId),
          duration);
    }
  }

  /**
   * 普通攻击
   *
   * @param player
   * @param skill
   * @param target
   */
  private void useCommonSkill(Player player, Skill skill, CreatureObject target) {
    SkillResource skillResource = skillManager.getSkillResourceById(skill.getSkillId());
    player
        .getCdComponent()
        .setSkillCd(skill.getSkillId(), System.currentTimeMillis() + skillResource.getCd());

    // 减少mp
    player.setMp(player.getMp() - skillResource.getMp());
    // 计算攻击力
    Long attack =
        player.getAttributeContainer().getAttributeValue(AttributeTypeEnum.ATTACK)
            + skillResource.getAttack();
    // 设置被攻击对象血量
    target.setHp(target.getHp() - attack);
    logger.info("[{}]受到攻击,减少血量[{}]", target.getId(), attack);
    if (target.getHp() <= 0) {
      if (target instanceof Monster) {
        // 怪物死亡 触发事件
        EventBusManager.submitEvent(
            MonsterDeadEvent.valueOf(
                player,
                target.getSceneId(),
                target.getId(),
                ((Monster) target).getMonsterResourceId()));
      }
    }
  }

  /**
   * 获取攻击对象
   *
   * @param player
   * @param skillResource
   * @return
   */
  private CreatureObject getAttackTarget(Player player, SkillResource skillResource) {
    int x = player.getX();
    int y = player.getY();
    SceneObjectView sceneObjectView = player.getSceneObjectView();
    Map<Long, SceneObject> sceneObjectMap = sceneObjectView.getSceneObjectMap();
    for (Map.Entry<Long, SceneObject> entry : sceneObjectMap.entrySet()) {
      SceneObject sceneObject = entry.getValue();
      if (sceneObject instanceof CreatureObject) {
        CreatureObject creatureObject = (CreatureObject) sceneObject;
        int targetX = creatureObject.getX();
        int targetY = creatureObject.getY();
        int distance = computeDistance(x, y, targetX, targetY);
        if (distance <= skillResource.getAttackRadius()) {
          return creatureObject;
        }
      }
    }
    return null;
  }

  /**
   * 获取攻击攻击对象列表
   *
   * @param player
   * @param skillResource
   * @return
   */
  private List<CreatureObject> getAttackTargetList(Player player, SkillResource skillResource) {
    ArrayList<CreatureObject> creatureList = new ArrayList<>();
    int x = player.getX();
    int y = player.getY();
    SceneObjectView sceneObjectView = player.getSceneObjectView();
    Map<Long, SceneObject> sceneObjectMap = sceneObjectView.getSceneObjectMap();
    for (Map.Entry<Long, SceneObject> entry : sceneObjectMap.entrySet()) {
      SceneObject sceneObject = entry.getValue();
      if (sceneObject instanceof CreatureObject) {
        CreatureObject creatureObject = (CreatureObject) sceneObject;
        int targetX = creatureObject.getX();
        int targetY = creatureObject.getY();
        int distance = computeDistance(x, y, targetX, targetY);
        if (distance <= skillResource.getAttackRadius()) {
          creatureList.add(creatureObject);
        }
      }
    }
    return creatureList;
  }

  /**
   * 计算两个对象的距离
   *
   * @param x
   * @param y
   * @param targetX
   * @param targetY
   * @return
   */
  private int computeDistance(int x, int y, int targetX, int targetY) {
    int distanceSquare = Math.abs(x * x - targetX * targetX) + Math.abs(y * y + targetY * targetY);
    return (int) Math.sqrt(distanceSquare);
  }
}
