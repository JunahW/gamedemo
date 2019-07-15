package com.example.gamedemo.server.game.task.storage;

import com.example.gamedemo.server.game.task.model.Task;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description：任务容器
 * @date 2019/7/15
 */
public class TaskStorage {
  /** 正在执行的任务 */
  private Map<Integer, Task> inExecuteTask = new HashMap<>();

  public Map<Integer, Task> getInExecuteTask() {
    return inExecuteTask;
  }

  public void setInExecuteTask(Map<Integer, Task> inExecuteTask) {
    this.inExecuteTask = inExecuteTask;
  }
}
