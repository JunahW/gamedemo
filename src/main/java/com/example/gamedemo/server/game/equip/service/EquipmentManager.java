package com.example.gamedemo.server.game.equip.service;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityBuilder;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.equip.entity.EquipStorageEnt;
import com.example.gamedemo.server.game.equip.resource.EquipAttrResource;
import com.example.gamedemo.server.game.equip.resource.EquipEnhanceResource;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wengj
 * @description：装备资源管理层
 * @date 2019/5/30
 */
@Component
@Order(3)
public class EquipmentManager {

  /** 静态资源 */
  private Map<String, EquipAttrResource> equipAttrResource =
      ResourceManager.getResourceMap(EquipAttrResource.class);

  private Map<String, EquipEnhanceResource> equipEnhanceResource =
      ResourceManager.getResourceMap(EquipEnhanceResource.class);

  /** <位置，<等级，配置资源>> */
  private Map<Integer, Map<Integer, EquipEnhanceResource>> positionLevelResourceMap;

  @Autowired private Accessor accessor;

  private EntityCacheServiceImpl<Long, EquipStorageEnt> entityCacheService =
      new EntityCacheServiceImpl<>();

  @PostConstruct
  public void init() {
    entityCacheService.setClazz(EquipStorageEnt.class);
    entityCacheService.setAccessor(accessor);
    initEquipEnhanceResource();
  }

  /**
   * 获取装备栏
   *
   * @param playerId
   * @return
   */
  public EquipStorageEnt getEquipStorageEnt(Long playerId) {
    EquipStorageEnt equipStorageEnt =
        entityCacheService.loadOrCreate(
            playerId,
            new EntityBuilder<Long, EquipStorageEnt>() {
              @Override
              public EquipStorageEnt newInstance(Long id) {
                EquipStorageEnt equipStorageEnt = new EquipStorageEnt();
                EquipStorage bar = new EquipStorage();
                equipStorageEnt.setEquipStorage(bar);
                equipStorageEnt.setId(playerId);
                return equipStorageEnt;
              }
            });
    return equipStorageEnt;
  }

  /**
   * 存储至数据库
   *
   * @param equipStorageEnt
   */
  public void saveEquipStorageEnt(EquipStorageEnt equipStorageEnt) {
    entityCacheService.writeBack(equipStorageEnt.getEntityId(), equipStorageEnt);
  }

  /**
   * 通过id获取装备的属性资源
   *
   * @param resourceId
   * @return
   */
  public EquipAttrResource getEquipAttrResourceById(int resourceId) {
    return equipAttrResource.get(resourceId);
  }

  /**
   * 通过部位和等级获取强化配置
   *
   * @param position
   * @param level
   * @return
   */
  public EquipEnhanceResource getEquipEnhanceResourceByPositionAndLevel(int position, int level) {
    Map<Integer, EquipEnhanceResource> positionLevelResource =
        positionLevelResourceMap.get(position);
    if (null != positionLevelResource) {
      EquipEnhanceResource equipEnhanceResource = positionLevelResource.get(level);
      return equipEnhanceResource;
    }
    return null;
  }

  /** 初始化equipEnhanceResource */
  private void initEquipEnhanceResource() {
    Map<Integer, Map<Integer, EquipEnhanceResource>> positionLevelResourceMap = new HashMap<>(16);

    Set<Map.Entry<String, EquipEnhanceResource>> entries = equipEnhanceResource.entrySet();
    for (Map.Entry<String, EquipEnhanceResource> enhanceResourceEntry : entries) {
      EquipEnhanceResource resourceValue = enhanceResourceEntry.getValue();
      int position = resourceValue.getPosition();
      if (!positionLevelResourceMap.containsKey(position)) {
        positionLevelResourceMap.put(position, new ConcurrentHashMap<>(16));
      }
      Map<Integer, EquipEnhanceResource> positionLevelResource =
          positionLevelResourceMap.get(position);

      int level = resourceValue.getLevel();
      if (!positionLevelResource.containsKey(level)) {
        positionLevelResource.put(level, resourceValue);
      }
    }
    this.positionLevelResourceMap = positionLevelResourceMap;
  }
}
