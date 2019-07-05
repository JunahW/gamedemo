package com.example.gamedemo.common.executer;

/**
 * @author wengj
 * @description
 * @date 2019/7/1
 */
public abstract class SceneCommand extends AbstractCommand {
  /** 场景id */
  private int sceneId;

  public SceneCommand(int sceneId) {
    this.sceneId = sceneId;
  }

  @Override
  public abstract void doAction();

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
