package com.example.gamedemo.server.game.monster.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;
import com.example.gamedemo.server.game.monster.event.MonsterDeadEvent;
import com.example.gamedemo.server.game.monster.packet.CM_CheckMonster;
import com.example.gamedemo.server.game.monster.packet.CM_MonsterDead;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wengj
 * @description:怪物控制层
 * @date 2019/6/14
 */
@Component
@HandlerClass
public class MonsterController {

  /**
   * 查看场景中的怪物
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "checkMonster")
  public void checkMonsters(TSession session, CM_CheckMonster req) {
    Map<Long, SceneObject> monsters =
        SpringContext.getMonsterService().getMonsters(req.getSceneId());
    SessionManager.sendMessage(session, monsters);
  }

  @HandlerMethod(cmd = "monsterDead")
  public void monsterDeadEvent(TSession session, CM_MonsterDead req) {
    EventBusManager.submitEvent(MonsterDeadEvent.valueOf(req.getSceneId(), req.getMonsterId()));
  }
}
