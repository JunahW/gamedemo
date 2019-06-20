package com.example.gamedemo.server.game.monster.service;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.monster.resource.MonsterResource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/6/14
 */
@Component
public class MonsterManager {
  private Map<Integer, MonsterResource> monsterResource =
      ResourceManager.getResourceMap(MonsterResource.class);

  /**
   * 获取配置资源
   *
   * @param resourceId
   * @return
   */
  public MonsterResource getMonsterResource(Integer resourceId) {
    return monsterResource.get(resourceId);
  }
}
