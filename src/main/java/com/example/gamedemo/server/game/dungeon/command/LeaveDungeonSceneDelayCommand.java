package com.example.gamedemo.server.game.dungeon.command;

import com.example.gamedemo.common.executer.scene.impl.AbstractSceneDelayCommand;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;

/**
 * @author wengj
 * @description：离开副本任务
 * @date 2019/7/15
 */
public class LeaveDungeonSceneDelayCommand extends AbstractSceneDelayCommand {
  /** 玩家 */
  private Player player;

  public LeaveDungeonSceneDelayCommand(int sceneId, long delay, Player player) {
    super(sceneId, delay);
    this.player = player;
  }

  public LeaveDungeonSceneDelayCommand(Scene scene, long delay, Player player) {
    super(scene, delay);
    this.player = player;
  }

  /**
   * @param sceneId
   * @param delay
   * @param player
   * @return
   */
  public static LeaveDungeonSceneDelayCommand valueOf(int sceneId, long delay, Player player) {
    return new LeaveDungeonSceneDelayCommand(sceneId, delay, player);
  }

  @Override
  public void doAction() {
    // 切换地图
    SpringContext.getSceneService().changeScene(player, player.getBeforeSceneId());
  }
}
