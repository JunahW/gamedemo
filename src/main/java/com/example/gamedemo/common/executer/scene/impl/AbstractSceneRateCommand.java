package com.example.gamedemo.common.executer.scene.impl;

/**
 * @author wengj
 * @description：场景周期指令
 * @date 2019/7/8
 */
public abstract class AbstractSceneRateCommand extends AbstractSceneCommand {
  /** 周期 */
  private long period;

  public AbstractSceneRateCommand(int sceneId, long period) {
    super(sceneId);
    this.period = period;
  }

  public long getPeriod() {
    return period;
  }

  public void setPeriod(long period) {
    this.period = period;
  }
}
