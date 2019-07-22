package com.example.gamedemo.server.game.rank.service;

import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.rank.event.ChangeBattleScoreEvent;

/**
 * @author: wengj
 * @date: 2019/7/22
 * @description: 排行板业务接口
 */
public interface RankService {
  /**
   * 展示排行板
   *
   * @param player
   * @param index
   */
  void showRank(Player player, int index);

  /**
   * 处理战力更新事件
   *
   * @param event
   */
  void handleChangeBattleScoreEvent(ChangeBattleScoreEvent event);

  /** 初始化各类排行板 */
  void initRanks();
}
