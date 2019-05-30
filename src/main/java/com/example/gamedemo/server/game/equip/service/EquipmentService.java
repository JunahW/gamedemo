package com.example.gamedemo.server.game.equip.service;

import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.bag.model.EquipItem;
import com.example.gamedemo.server.game.equip.entity.EquipStorageEnt;

/**
 * @author: wengj
 * @date: 2019/5/30
 * @description: 装备业务接口
 */
public interface EquipmentService {
    /**
     * 需要实现穿装备、脱装备、装备替换、查看装备信息等基础功能
     * 	需要支持穿戴要求，不满足条件的装备无法穿戴
     */

    /**
     * 穿装备
     *
     * @param account
     * @param equipId
     * @return
     */
    boolean equip(Account account, long equipId);

    /**
     * 脱下装备
     *
     * @param account
     * @param equipId
     * @return
     */
    boolean unEquip(Account account, long equipId);

    /**
     * 获取装备信息
     *
     * @param account
     * @param guid
     * @return
     */
    EquipItem getEquipItemByGuid(Account account, long guid);

    /**
     * 保存装备栏的数据
     *
     * @param account
     */
    void saveEquipmentStorageEnt(Account account);

    /**
     * 获取装备栏
     *
     * @param accountId
     * @return
     */
    EquipStorageEnt getEquipStorageEnt(String accountId);


}
