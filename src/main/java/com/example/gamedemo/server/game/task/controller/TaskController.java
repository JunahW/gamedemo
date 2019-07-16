package com.example.gamedemo.server.game.task.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.anno.ReceiverHandler;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.player.event.PlayerLoadEvent;
import com.example.gamedemo.server.game.task.event.TaskEvent;
import com.example.gamedemo.server.game.task.packet.CM_GetTask;
import com.example.gamedemo.server.game.task.packet.CM_RewardTask;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/7/15
 */
@Component
@HandlerClass
public class TaskController {
  /**
   * 监听玩家加载事件
   *
   * @param event
   */
  @ReceiverHandler
  public void handlePlayerLoadEvent(PlayerLoadEvent event) {
    SpringContext.getTaskService().handlePlayerLoadEvent(event);
  }

  /**
   * 获取任务信息
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "getTask")
  public void getTask(TSession session, CM_GetTask req) {
    SpringContext.getTaskService().getTaskInfo(session.getPlayer());
  }

  /**
   * 任务完成接受奖励
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "rewardTask")
  public void rewardTaskAfterFinish(TSession session, CM_RewardTask req) {
    SpringContext.getTaskService().rewardTaskAfterFinish(session.getPlayer(), req.getTaskId());
  }

  /**
   * 处理任务事件
   *
   * @param event
   */
  @ReceiverHandler
  public void handleTaskEvent(TaskEvent event) {
    SpringContext.getTaskService().handleTaskEvent(event);
  }
}
