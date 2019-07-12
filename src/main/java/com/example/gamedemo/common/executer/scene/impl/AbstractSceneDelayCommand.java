package com.example.gamedemo.common.executer.scene.impl;

import com.example.gamedemo.server.game.scene.model.Scene;

/**
 * @author wengj
 * @description：场景延时指令
 * @date 2019/7/8
 */
public abstract class AbstractSceneDelayCommand extends AbstractSceneCommand {
  /** 延时 */
  private long delay;

  public AbstractSceneDelayCommand(int sceneId, long delay) {
    super(sceneId);
    this.delay = delay;
  }

  public AbstractSceneDelayCommand(Scene scene, long delay) {
    super(scene.getSceneResourceId());
    this.delay = delay;
  }

  public long getDelay() {
    return delay;
  }

  public void setDelay(long delay) {
    this.delay = delay;
  }
}
