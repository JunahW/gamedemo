package com.example.gamedemo.server.game.equip.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.anno.ReceiverHandler;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.packet.SM_ErrorCode;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.equip.packet.*;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import com.example.gamedemo.server.game.player.event.PlayerLoadEvent;
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
    boolean equip = false;
    try {
      equip = SpringContext.getEquipmentService().equip(player, req.getGuid());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (equip) {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("穿上装备成功"));
    }
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
    boolean flag = false;
    try {
      flag = SpringContext.getEquipmentService().unEquip(player, req.getPosition());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (flag) {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("脱下装备"));
    }
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
    AbstractItem equipItem =
        SpringContext.getEquipmentService().getEquipItemByGuid(player, req.getGuid());
    SessionManager.sendMessage(session, equipItem);
  }

  /**
   * 获取装备信息
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "positionEquip")
  public void getEquipmentByPosition(TSession session, CM_GetPositionEquip req) {
    Player player = session.getPlayer();
    AbstractItem equipItem =
        SpringContext.getEquipmentService().getEquipItemByPosition(player, req.getPosition());
    SessionManager.sendMessage(session, equipItem);
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
    SessionManager.sendMessage(session, equipBar);
  }

  /**
   * 装备卡槽升级
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "equipEnhance")
  public void equipEnhance(TSession session, CM_EquipEnhance req) {
    Player player = session.getPlayer();
    boolean enhanceEquip = false;
    try {
      enhanceEquip = SpringContext.getEquipmentService().enhanceEquip(player, req.getPosition());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (enhanceEquip) {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("升级装备成功"));
    }
  }

  /**
   * 查看部位等级
   *
   * @param session
   * @param rep
   */
  @HandlerMethod(cmd = "checkPosition")
  public void checkPosition(TSession session, CM_CheckPosition rep) {}

  /**
   * 处理用户加载事件
   *
   * @param event
   */
  @ReceiverHandler
  public void handlerPlayerLoadEvent(PlayerLoadEvent event) {
    SpringContext.getEquipmentService().computeEquipModelAttributes(event);
  }

  /**
   * 处理用户加载事件
   *
   * @param event
   */
  @ReceiverHandler
  public void handlerPlayerLoadEvent2(PlayerLoadEvent event) {
    SpringContext.getEquipmentService().computeEquipEnhanceModelAttributes(event);
  }
}
