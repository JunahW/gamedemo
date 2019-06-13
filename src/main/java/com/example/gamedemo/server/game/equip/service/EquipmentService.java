package com.example.gamedemo.server.game.equip.service;

import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.equip.entity.EquipStorageEnt;
import com.example.gamedemo.server.game.equip.resource.EquipAttrResource;
import com.example.gamedemo.server.game.player.event.PlayerLoadEvent;
import com.example.gamedemo.server.game.player.model.Player;

/**
 * @author: wengj
 * @date: 2019/5/30
 * @description: 装备业务接口
 */
public interface EquipmentService {
  /** 需要实现穿装备、脱装备、装备替换、查看装备信息等基础功能 需要支持穿戴要求，不满足条件的装备无法穿戴 */

  /**
   * 穿装备
   *
   * @param player
   * @param equipId
   * @return
   */
  boolean equip(Player player, long equipId);

  /**
   * 脱下装备
   *
   * @param player
   * @param position
   * @return
   */
  boolean unEquip(Player player, int position);

  /**
   * 获取装备信息
   *
   * @param player
   * @param guid
   * @return
   */
  AbstractItem getEquipItemByGuid(Player player, long guid);

  /**
   * 获取部位的装备信息
   *
   * @param player
   * @param position
   * @return
   */
  AbstractItem getEquipItemByPosition(Player player, int position);

  /**
   * 保存装备栏的数据
   *
   * @param player
   */
  void saveEquipmentStorageEnt(Player player);

  /**
   * 获取装备栏
   *
   * @param playerId
   * @return
   */
  EquipStorageEnt getEquipStorageEnt(String playerId);

  /**
   * 增强属性
   *
   * @param player
   * @param position
   * @return
   */
  boolean enhanceEquip(Player player, int position);

  /**
   * 获取装备的增强属性
   *
   * @param id
   * @return
   */
  EquipAttrResource getEquipAttrResourceById(int id);

  /**
   * 计算玩家的装备属性集
   *
   * @param event
   */
  void computeEquipModelAttributes(PlayerLoadEvent event);

  /**
   * 计算玩家的装备模块
   *
   * @param event
   */
  void computeEquipEnhanceModelAttributes(PlayerLoadEvent event);
}
