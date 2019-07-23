package com.example.gamedemo.server.game.task.storage;

import com.example.gamedemo.server.game.task.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wengj
 * @description：任务容器
 * @date 2019/7/15
 */
public class TaskStorage {
  /** 正在执行的任务 */
  private Map<Integer, Task> inExecuteTask = new HashMap<>();

  /** 等待触发的任务 */
  private Map<Integer, Task> inTriggerTask = new HashMap<>();

  /** 已完成的任务 */
  private List<Integer> finishTaskList = new ArrayList<>();

  public Map<Integer, Task> getInExecuteTask() {
    return inExecuteTask;
  }

  public void setInExecuteTask(Map<Integer, Task> inExecuteTask) {
    this.inExecuteTask = inExecuteTask;
  }

  public Map<Integer, Task> getInTriggerTask() {
    return inTriggerTask;
  }

  public void setInTriggerTask(Map<Integer, Task> inTriggerTask) {
    this.inTriggerTask = inTriggerTask;
  }

  public List<Integer> getFinishTaskList() {
    return finishTaskList;
  }

  public void setFinishTaskList(List<Integer> finishTaskList) {
    this.finishTaskList = finishTaskList;
  }

  /**
   * 新增task
   *
   * @param task
   */
  public void putExecuteTask(Task task) {
    inExecuteTask.put(task.getTaskId(), task);
  }

  /**
   * 移除任务
   *
   * @param taskId
   */
  public void removeExecuteTTask(Integer taskId) {
    inExecuteTask.remove(taskId);
  }

  /**
   * 获取任务
   *
   * @param taskId
   * @return
   */
  public Task getExecuteTTaskById(Integer taskId) {
    return inExecuteTask.get(taskId);
  }

  /**
   * 新增待触发task
   *
   * @param task
   */
  public void putTriggerTask(Task task) {
    inTriggerTask.put(task.getTaskId(), task);
  }

  /**
   * 移除待触发任务
   *
   * @param taskId
   */
  public void removeTriggerTask(Integer taskId) {
    inTriggerTask.remove(taskId);
  }

  /**
   * 获取待触发任务
   *
   * @param taskId
   * @return
   */
  public Task getTriggerTaskById(Integer taskId) {
    return inTriggerTask.get(taskId);
  }

  /**
   * 添加已完成的任务
   *
   * @param taskId
   */
  public void addFinishTask(Integer taskId) {
    finishTaskList.add(taskId);
  }
}
