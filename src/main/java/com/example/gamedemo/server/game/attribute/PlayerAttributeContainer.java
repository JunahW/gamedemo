package com.example.gamedemo.server.game.attribute;

import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.player.model.Player;

import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description:玩家属性容器
 * @date 2019/6/4
 */
public class PlayerAttributeContainer extends AbstractAttributeContainer<Player> {
  public PlayerAttributeContainer(Player player) {
    super(player);
  }

  /** 计算玩家战力 */
  @Override
  public void computeCombatIndex() {
    long combatIndex = 0;
    ConcurrentMap<AttributeTypeEnum, Attribute> attributeMap = this.getAttributeMap();
    /** 计算公式：战力 = 血量*50 + 魔法值*100 + 攻击力*300 + 防御力*200 */
    Attribute hpAttribute = attributeMap.get(AttributeTypeEnum.HP);
    Attribute mpAttribute = attributeMap.get(AttributeTypeEnum.MP);
    Attribute attackAttribute = attributeMap.get(AttributeTypeEnum.ATTACK);
    Attribute defenseAttribute = attributeMap.get(AttributeTypeEnum.DEFENSE);

    combatIndex =
        hpAttribute.getValue() * 50
            + mpAttribute.getValue() * 100
            + attackAttribute.getValue() * 300
            + defenseAttribute.getValue() * 200;
    /** 设置战力 */
    owner.setCombatIndex(combatIndex);
  }
}
