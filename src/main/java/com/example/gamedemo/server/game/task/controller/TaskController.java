package com.example.gamedemo.server.game.task.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.anno.ReceiverHandler;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.task.event.TaskEvent;
import com.example.gamedemo.server.game.task.packet.CM_GetTask;
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
   * 获取任务信息
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "getTask")
  public void getTask(TSession session, CM_GetTask req) {}

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
