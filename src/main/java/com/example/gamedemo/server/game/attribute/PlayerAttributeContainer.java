package com.example.gamedemo.server.game.attribute;

import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.attribute.utils.AttributeUtils;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.rank.event.ChangeBattleScoreEvent;
import com.example.gamedemo.server.game.rank.model.BattleScoreRankInfo;

import java.util.Map;

/**
 * @author wengj
 * @description:玩家属性容器
 * @date 2019/6/4
 */
public class PlayerAttributeContainer extends AbstractAttributeContainer<Player> {
  public PlayerAttributeContainer(Player player) {
    super(player);
  }

  public PlayerAttributeContainer() {}

  /** 计算玩家战力 */
  @Override
  public void computeCombatIndex() {

    Map<AttributeTypeEnum, Attribute> finalAttributeMap = this.getFinalAttributeMap();
    /** 计算战力 */
    long combatIndex = AttributeUtils.computeCombatIndex(finalAttributeMap);
    owner.setCombatIndex(combatIndex);
    // 抛出更新战力事件
    BattleScoreRankInfo rankInfo = BattleScoreRankInfo.valueOf(owner.getId(), combatIndex);
    EventBusManager.submitEvent(ChangeBattleScoreEvent.valueOf(rankInfo));
  }
}
