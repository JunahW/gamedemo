package com.example.gamedemo.common.executer.scene;

import com.example.gamedemo.common.executer.Command;

/**
 * @author wengj
 * @description：场景务执行服务接口
 * @date 2019/7/8
 */
public interface ISceneExecutorService {
  void submit(Command command);
}
