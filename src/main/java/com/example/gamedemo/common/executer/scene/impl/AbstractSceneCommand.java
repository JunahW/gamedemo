package com.example.gamedemo.common.executer.scene.impl;

import com.example.gamedemo.common.executer.AbstractCommand;

/**
 * @author wengj
 * @description
 * @date 2019/7/1
 */
public abstract class AbstractSceneCommand extends AbstractCommand {
  /** 场景id */
  private int sceneId;

  public AbstractSceneCommand(int sceneId) {
    this.sceneId = sceneId;
  }

  @Override
  public void doAction() {}

  @Override
  public int modIndex(int poolSize) {
    return getSceneId() % poolSize;
  }

  public int getSceneId() {
    return sceneId;
  }

  public void setSceneId(int sceneId) {
    this.sceneId = sceneId;
  }
}
