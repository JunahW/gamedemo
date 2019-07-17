package com.example.gamedemo.server.game.dungeon.command;

import com.example.gamedemo.common.executer.scene.impl.AbstractSceneDelayCommand;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.dungeon.packet.SM_DungeonCountDown;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;

/**
 * @author wengj
 * @description：离开副本倒计时任务
 * @date 2019/7/15
 */
public class LeaveDungeonSceneNotifyDelayCommand extends AbstractSceneDelayCommand {
  /** \ 倒计时 */
  private int countDown;

  /** 玩家 */
  private Player player;

  public LeaveDungeonSceneNotifyDelayCommand(
      int sceneId, long delay, int countDown, Player player) {
    super(sceneId, delay);
    this.countDown = countDown;
    this.player = player;
  }

  public LeaveDungeonSceneNotifyDelayCommand(
      Scene scene, long delay, int countDown, Player player) {
    super(scene, delay);
    this.countDown = countDown;
    this.player = player;
  }

  /**
   * @param sceneId
   * @param delay
   * @param countDown
   * @param player
   * @return
   */
  public static LeaveDungeonSceneNotifyDelayCommand valueOf(
      int sceneId, long delay, int countDown, Player player) {
    return new LeaveDungeonSceneNotifyDelayCommand(sceneId, delay, countDown, player);
  }

  @Override
  public void doAction() {
    LeaveDungeonSceneDelayCommand command =
        LeaveDungeonSceneDelayCommand.valueOf(getSceneId(), countDown, player);
    Scene scene = SpringContext.getSceneService().getSceneById(player, getSceneId());
    scene.putCommand(command);
    // 通知客户端
    SessionManager.sendMessage(player, SM_DungeonCountDown.valueOf(countDown));
    // 提交退出副本任务
    SpringContext.getSceneExecutorService().submit(command);
  }
}
