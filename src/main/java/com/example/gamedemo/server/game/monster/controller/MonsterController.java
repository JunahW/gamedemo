package com.example.gamedemo.server.game.monster.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.monster.packet.CM_CheckMonster;
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
    Map<Long, Monster> monsters = SpringContext.getMonsterService().getMonsters(req.getSceneId());
    SessionManager.sendMessage(session, monsters);
  }
}
