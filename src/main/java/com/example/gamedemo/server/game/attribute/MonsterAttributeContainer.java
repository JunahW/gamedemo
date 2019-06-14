package com.example.gamedemo.server.game.attribute;

import com.example.gamedemo.server.game.monster.model.Monster;

/**
 * @author wengj
 * @description:怪物属性容器
 * @date 2019/6/4
 */
public class MonsterAttributeContainer extends AbstractAttributeContainer<Monster> {
  public MonsterAttributeContainer(Monster monster) {
    super(monster);
  }

  @Override
  public void computeCombatIndex() {}
}
