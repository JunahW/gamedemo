package com.example.gamedemo.client.dispatcher;

import com.example.gamedemo.client.handler.DefaultHandler;
import com.example.gamedemo.client.handler.FightHandler;
import com.example.gamedemo.client.handler.SceneHandler;
import com.example.gamedemo.server.game.scene.packet.SM_Aoi;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/7/11
 */
public class MessageDispatcher {
  private static Map<Class, IHandler> classClientInvokeMethodMap = new HashMap<>();

  static {
    DefaultHandler defaultHandler = new DefaultHandler();
    MessageDispatcher.putMessageHandler(DefaultHandler.class, defaultHandler::defaultMethod);

    SceneHandler sceneHandler = new SceneHandler();
    MessageDispatcher.putMessageHandler(SM_Aoi.class, sceneHandler::handAoiMessage);

    FightHandler fightHandler = new FightHandler();
    // MessageDispatcher.putMessageHandler();
  }

  /**
   * 新增
   *
   * @param clazz
   * @param invokeMethod
   */
  public static void putMessageHandler(Class clazz, IHandler invokeMethod) {
    classClientInvokeMethodMap.put(clazz, invokeMethod);
  }

  /**
   * 获取
   *
   * @param clazz
   * @return
   */
  private static IHandler getMessageHandler(Class clazz) {
    IHandler iHandler = classClientInvokeMethodMap.get(clazz);
    if (iHandler == null) {
      return classClientInvokeMethodMap.get(DefaultHandler.class);
    }
    return iHandler;
  }

  /**
   * 执行
   *
   * @param message
   */
  public static void execute(Object message) {
    Class<?> clazz = message.getClass();
    IHandler messageHandler = getMessageHandler(clazz);
    messageHandler.handleMessage(message);
  }
}
