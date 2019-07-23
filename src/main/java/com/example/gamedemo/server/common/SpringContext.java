package com.example.gamedemo.server.common;

import com.example.gamedemo.common.executer.account.IAccountExecutorService;
import com.example.gamedemo.common.executer.scene.ISceneExecutorService;
import com.example.gamedemo.common.executer.schedule.ScheduleService;
import com.example.gamedemo.server.common.service.GlobalService;
import com.example.gamedemo.server.game.account.service.AccountService;
import com.example.gamedemo.server.game.bag.service.ItemService;
import com.example.gamedemo.server.game.buff.service.BuffService;
import com.example.gamedemo.server.game.dungeon.service.DungeonService;
import com.example.gamedemo.server.game.equip.service.EquipmentService;
import com.example.gamedemo.server.game.fight.service.FightService;
import com.example.gamedemo.server.game.guild.service.GuildService;
import com.example.gamedemo.server.game.monster.service.MonsterService;
import com.example.gamedemo.server.game.player.service.PlayerService;
import com.example.gamedemo.server.game.rank.service.RankService;
import com.example.gamedemo.server.game.scene.service.SceneService;
import com.example.gamedemo.server.game.skill.service.SkillService;
import com.example.gamedemo.server.game.task.receiver.TaskReceiverHandler;
import com.example.gamedemo.server.game.task.service.TaskService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wengj
 * @description
 * @date 2019/5/29
 */
@Component
public class SpringContext implements ApplicationContextAware {
  private static SpringContext instance;
  @Autowired private ApplicationContext applicationContext;
  @Autowired private ItemService itemService;
  @Autowired private EquipmentService equipmentService;
  @Autowired private PlayerService playerService;
  @Autowired private AccountService accountService;
  @Autowired private SceneService sceneService;
  @Autowired private MonsterService monsterService;
  @Autowired private SkillService skillService;
  @Autowired private BuffService buffService;
  @Autowired private FightService fightService;
  @Autowired private ISceneExecutorService sceneExecutorService;
  @Autowired private IAccountExecutorService accountExecutorService;
  @Autowired private ScheduleService scheduleService;
  @Autowired private DungeonService dungeonService;
  @Autowired private TaskService taskService;
  @Autowired private GuildService guildService;
  @Autowired private RankService rankService;
  @Autowired private TaskReceiverHandler taskReceiverHandler;
  @Autowired private GlobalService globalService;

  public static ItemService getItemService() {
    return instance.itemService;
  }

  public static EquipmentService getEquipmentService() {
    return instance.equipmentService;
  }

  public static PlayerService getPlayerService() {
    return instance.playerService;
  }

  public static AccountService getAccountService() {
    return instance.accountService;
  }

  public static SceneService getSceneService() {
    return instance.sceneService;
  }

  public static MonsterService getMonsterService() {
    return instance.monsterService;
  }

  public static SkillService getSkillService() {
    return instance.skillService;
  }

  public static BuffService getBuffService() {
    return instance.buffService;
  }

  public static FightService getFightService() {
    return instance.fightService;
  }

  public static ISceneExecutorService getSceneExecutorService() {
    return instance.sceneExecutorService;
  }

  public static IAccountExecutorService getAccountExecutorService() {
    return instance.accountExecutorService;
  }

  public static ScheduleService getScheduleService() {
    return instance.scheduleService;
  }

  public static DungeonService getDungeonService() {
    return instance.dungeonService;
  }

  public static TaskService getTaskService() {
    return instance.taskService;
  }

  public static GuildService getGuildService() {
    return instance.guildService;
  }

  public static RankService getRankService() {
    return instance.rankService;
  }

  public static TaskReceiverHandler getTaskReceiverHandler() {
    return instance.taskReceiverHandler;
  }

  public static GlobalService getGlobalService() {
    return instance.globalService;
  }

  @PostConstruct
  public void init() {
    instance = this;
  }

  @Override
  public void setApplicationContext(ApplicationContext ac) throws BeansException {
    applicationContext = ac;
  }
}
