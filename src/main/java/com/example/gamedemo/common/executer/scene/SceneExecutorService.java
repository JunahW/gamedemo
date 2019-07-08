package com.example.gamedemo.common.executer.scene;

import com.example.gamedemo.common.executer.Command;
import com.example.gamedemo.common.executer.scene.impl.AbstractSceneDelayCommand;
import com.example.gamedemo.common.executer.scene.impl.AbstractSceneRateCommand;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description：账号任务执行服务
 * @date 2019/7/8
 */
@Component
public class SceneExecutorService implements ISceneExecutorService {
  @Override
  public void submit(Command command) {
    if (command instanceof AbstractSceneRateCommand) {
      // SceneExecutor.addScheduleTask();
    } else if (command instanceof AbstractSceneDelayCommand) {
      // 延时任务
    } else {
      // 其他任务
    }
  }
}
