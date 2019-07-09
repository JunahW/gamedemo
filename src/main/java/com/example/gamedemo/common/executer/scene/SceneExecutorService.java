package com.example.gamedemo.common.executer.scene;

import com.example.gamedemo.common.executer.Command;
import com.example.gamedemo.common.executer.scene.impl.AbstractSceneDelayCommand;
import com.example.gamedemo.common.executer.scene.impl.AbstractSceneRateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description：账号任务执行服务
 * @date 2019/7/8
 */
@Component
public class SceneExecutorService implements ISceneExecutorService {

  @Autowired private SceneExecutor sceneExecutor;

  @Override
  public void submit(Command command) {
    if (command instanceof AbstractSceneRateCommand) {
      AbstractSceneRateCommand sceneRateCommand = (AbstractSceneRateCommand) command;
      sceneExecutor.addScheduleTask(sceneRateCommand);
    } else if (command instanceof AbstractSceneDelayCommand) {
      AbstractSceneDelayCommand sceneDelayCommand = (AbstractSceneDelayCommand) command;
      sceneExecutor.addDelayTask(sceneDelayCommand);
    } else {
      sceneExecutor.addTask(command);
    }
  }
}
