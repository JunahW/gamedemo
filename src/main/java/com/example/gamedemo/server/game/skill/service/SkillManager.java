package com.example.gamedemo.server.game.skill.service;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityBuilder;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.skill.entity.SkillStorageEnt;
import com.example.gamedemo.server.game.skill.resource.SkillResource;
import com.example.gamedemo.server.game.skill.storage.SkillStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/6/20
 */
@Component
public class SkillManager {
  private Map<Integer, SkillResource> skillResource =
      ResourceManager.getResourceMap(SkillResource.class);

  @Autowired private Accessor accessor;

  private EntityCacheServiceImpl<Long, SkillStorageEnt> entityCacheService =
      new EntityCacheServiceImpl<>();

  @PostConstruct
  public void init() {
    entityCacheService.setClazz(SkillStorageEnt.class);
    entityCacheService.setAccessor(accessor);
  }

  /**
   * 获取技能栏
   *
   * @param playerId
   * @return
   */
  public SkillStorageEnt getSkillStorageEnt(Long playerId) {
    SkillStorageEnt skillStorageEnt =
        entityCacheService.loadOrCreate(
            playerId,
            new EntityBuilder<Long, SkillStorageEnt>() {
              @Override
              public SkillStorageEnt newInstance(Long id) {
                SkillStorageEnt skillStorageEnt = new SkillStorageEnt();
                SkillStorage skillStorage = new SkillStorage();
                skillStorageEnt.setSkillStorage(skillStorage);
                skillStorageEnt.setPlayerId(playerId);
                return skillStorageEnt;
              }
            });
    return skillStorageEnt;
  }
}
