package com.example.gamedemo.common.executer.scene.impl;

import com.example.gamedemo.server.game.scene.model.Scene;

/**
 * @author wengj
 * @description：场景周期指令
 * @date 2019/7/8
 */
public abstract class AbstractSceneRateCommand extends AbstractSceneCommand {

  /** 延时 */
  private long delay = 0L;
  /** 周期 */
  private long period;

  public AbstractSceneRateCommand(int sceneId, long period) {
    super(sceneId);
    this.period = period;
  }

  public AbstractSceneRateCommand(Scene scene, long period) {
    super(scene.getSceneResourceId());
    this.period = period;
  }

  public AbstractSceneRateCommand(int sceneId, long delay, long period) {
    super(sceneId);
    this.delay = delay;
    this.period = period;
  }

  public AbstractSceneRateCommand(Scene scene, long delay, long period) {
    super(scene.getSceneResourceId());
    this.delay = delay;
    this.period = period;
  }

  public long getPeriod() {
    return period;
  }

  public void setPeriod(long period) {
    this.period = period;
  }

  public long getDelay() {
    return delay;
  }

  public void setDelay(long delay) {
    this.delay = delay;
  }
}
