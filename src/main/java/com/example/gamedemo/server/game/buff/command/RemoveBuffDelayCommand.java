package com.example.gamedemo.server.game.buff.command;

import com.example.gamedemo.common.executer.scene.impl.AbstractSceneDelayCommand;
import com.example.gamedemo.server.game.buff.model.BuffContainer;

/**
 * @author wengj
 * @description
 * @date 2019/7/1
 */
public class RemoveBuffDelayCommand extends AbstractSceneDelayCommand {
  /** buff容器 */
  private BuffContainer buffContainer;

  /** 移除的buff */
  private Integer buffId;

  public RemoveBuffDelayCommand(
      int sceneId, long delay, BuffContainer buffContainer, Integer buffId) {
    super(sceneId, delay);
    this.buffContainer = buffContainer;
    this.buffId = buffId;
  }

  /**
   * @param sceneId
   * @param buffContainer
   * @param buffId
   * @return
   */
  public static RemoveBuffDelayCommand valueOf(
      int sceneId, long delay, BuffContainer buffContainer, Integer buffId) {
    RemoveBuffDelayCommand removeBuffCommand =
        new RemoveBuffDelayCommand(sceneId, delay, buffContainer, buffId);
    return removeBuffCommand;
  }

  @Override
  public void doAction() {
    buffContainer.removeBuff(buffId);
  }
}
