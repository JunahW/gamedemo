package com.example.gamedemo.server.game.task.processor;

import com.example.gamedemo.server.game.task.constant.TaskTypeEnum;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description:任务处理器
 * @date 2019/7/15
 */
public abstract class AbstractProcessor {

  /** 任务进程处理器集合 */
  private static final Map<TaskTypeEnum, AbstractProcessor> processorMap = new HashMap<>();

  /**
   * 获取任务进程处理器
   *
   * @param taskTypeEnum
   * @return
   */
  public static AbstractProcessor getProcessor(TaskTypeEnum taskTypeEnum) {
    return processorMap.get(taskTypeEnum);
  }

  /**
   * 获取任务类型
   *
   * @return
   */
  public abstract TaskTypeEnum getTaskTypeEnum();

  @PostConstruct
  private void init() {
    processorMap.put(getTaskTypeEnum(), this);
  }
}
