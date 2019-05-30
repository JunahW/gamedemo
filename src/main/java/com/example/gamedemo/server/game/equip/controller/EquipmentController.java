package com.example.gamedemo.server.game.equip.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.bag.model.EquipItem;
import com.example.gamedemo.server.game.equip.packet.CM_EquipItem;
import com.example.gamedemo.server.game.equip.packet.CM_GetEquipMsg;
import com.example.gamedemo.server.game.equip.packet.CM_UnEquipItem;
import com.example.gamedemo.server.game.equip.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description：装备控制器
 * @date 2019/5/30
 */
@Component
@HandlerClass
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;


    /**
     * 穿上装备
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "equip")
    public void equip(TSession session, CM_EquipItem req) {
        Account account = session.getAccount();
        equipmentService.equip(account, req.getGuid());
        System.out.println("装备道具");
    }

    /**
     * 卸下装备
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "unEquip")
    public void unEquip(TSession session, CM_UnEquipItem req) {
        Account account = session.getAccount();

        boolean flag = equipmentService.unEquip(account, req.getGuid());
        System.out.println("脱下装备");
        SessionManager.sendMessage(session, "脱下装备" + "\r\n");
    }

    /**
     * 获取装备信息
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "getEquip")
    public void getEquipmentMsg(TSession session, CM_GetEquipMsg req) {
        Account account = session.getAccount();
        EquipItem equipItem = equipmentService.getEquipItemByGuid(account, req.getGuid());
        SessionManager.sendMessage(session, "装备信息：" + equipItem + "\r\n");
        System.out.println("装备信息");

    }
}
