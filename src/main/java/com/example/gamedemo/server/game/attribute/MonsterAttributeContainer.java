package com.example.gamedemo.server.game.attribute;

import com.example.gamedemo.server.game.monster.model.Monster;

/**
 * @author wengj
 * @description
 * @date 2019/6/21
 */
public class MonsterAttributeContainer extends AbstractAttributeContainer<Monster> {

  public MonsterAttributeContainer(Monster owner) {
    super(owner);
  }

  public MonsterAttributeContainer() {}

  @Override
  public void computeCombatIndex() {}
}
