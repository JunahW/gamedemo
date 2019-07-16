package com.example.gamedemo.server.game.task.packet;

import com.example.gamedemo.server.game.task.model.TaskVO;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/7/16
 */
public class SM_TaskDetail {
  private List<TaskVO> taskVoList = new LinkedList<>();

  public void addTaskVo(TaskVO taskVO) {
    taskVoList.add(taskVO);
  }

  public List<TaskVO> getTaskVoList() {
    return taskVoList;
  }

  public void setTaskVoList(List<TaskVO> taskVoList) {
    this.taskVoList = taskVoList;
  }
}
