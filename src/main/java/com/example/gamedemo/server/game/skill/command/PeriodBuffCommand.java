package com.example.gamedemo.server.game.skill.command;

import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.common.executer.SceneCommand;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.monster.event.MonsterDeadEvent;
import com.example.gamedemo.server.game.monster.model.Monster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengj
 * @description
 * @date 2019/7/1
 */
public class PeriodBuffCommand extends SceneCommand {
  private static final Logger logger = LoggerFactory.getLogger(PeriodBuffCommand.class);
  /** 目标对象 */
  private CreatureObject target;

  /** 减少的hp */
  private long reduceHp;

  public PeriodBuffCommand(int sceneId) {
    super(sceneId);
  }

  public static PeriodBuffCommand valueOf(int sceneId, CreatureObject target, long reduceHp) {
    PeriodBuffCommand command = new PeriodBuffCommand(sceneId);
    command.setTarget(target);
    command.setReduceHp(reduceHp);
    return command;
  }

  @Override
  public void doAction() {
    logger.info("周期buff执行中");
    target.setHp(target.getHp() - reduceHp);
    if (target.getHp() <= 0) {
      if (target instanceof Monster) {
        // 怪物死亡 触发事件
        EventBusManager.submitEvent(MonsterDeadEvent.valueOf(target.getSceneId(), target.getId()));
      }
      cancel();
    }
  }

  public CreatureObject getTarget() {
    return target;
  }

  public void setTarget(CreatureObject target) {
    this.target = target;
  }

  public long getReduceHp() {
    return reduceHp;
  }

  public void setReduceHp(long reduceHp) {
    this.reduceHp = reduceHp;
  }
}
