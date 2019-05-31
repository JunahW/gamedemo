package com.example.gamedemo.server.game.equip.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.game.SpringContext;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.equip.packet.CM_EquipItem;
import com.example.gamedemo.server.game.equip.packet.CM_GetEquipMsg;
import com.example.gamedemo.server.game.equip.packet.CM_ShowEquipmentBar;
import com.example.gamedemo.server.game.equip.packet.CM_UnEquipItem;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import com.example.gamedemo.server.game.player.model.Player;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description：装备控制器
 * @date 2019/5/30
 */
@Component
@HandlerClass
public class EquipmentController {
    /**
     * 穿上装备
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "equip")
    public void equip(TSession session, CM_EquipItem req) {
        Player player = session.getPlayer();
        SpringContext.getEquipmentService().equip(player, req.getGuid());
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
        Player player = session.getPlayer();

        boolean flag = SpringContext.getEquipmentService().unEquip(player, req.getPosition());
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
        Player player = session.getPlayer();
        AbstractItem equipItem = SpringContext.getEquipmentService().getEquipItemByGuid(player, req.getGuid());
        SessionManager.sendMessage(session, "装备信息：" + equipItem + "\r\n");
        System.out.println("装备信息");

    }

    /**
     * 展示装备栏
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "showBar")
    public void showEquipment(TSession session, CM_ShowEquipmentBar req) {
        Player player = session.getPlayer();
        EquipStorage equipBar = player.getEquipBar();
        SessionManager.sendMessage(session, "装备栏：" + equipBar + "\r\n");

    }
}