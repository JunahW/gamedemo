package com.example.gamedemo.common.event;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.ReceiverHandler;
import com.example.gamedemo.common.utils.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author wengj
 * @description：事件总线
 * @date 2019/6/11
 */
public class EventBusManager {
  private static final Logger logger = LoggerFactory.getLogger(EventBusManager.class);

  /** 存放事件处理映射 */
  private static Map<Class<? extends Event>, List<ReceiverInvoke>> eventReceiverMap =
      new HashMap<>();

  /**
   * 提交事件并触发相应的方法
   *
   * @param event
   */
  public static void submitEvent(Event event) {
    List<ReceiverInvoke> receiverInvokeList = eventReceiverMap.get(event.getClass());
    for (ReceiverInvoke receiverInvoke : receiverInvokeList) {
      receiverInvoke.invoke(event);
    }
  }

  /**
   * 注册执行方法
   *
   * @param clazz
   * @param receiverInvoke
   */
  public static void registerReceiverInvoke(
      Class<? extends Event> clazz, ReceiverInvoke receiverInvoke) {
    if (eventReceiverMap.containsKey(clazz)) {
      List<ReceiverInvoke> receiverInvokes = eventReceiverMap.get(clazz);
      if (!receiverInvokes.contains(receiverInvoke)) {
        receiverInvokes.add(receiverInvoke);
      }
    } else {
      LinkedList<ReceiverInvoke> receiverInvokes = new LinkedList<>();
      receiverInvokes.add(receiverInvoke);
      eventReceiverMap.put(clazz, receiverInvokes);
    }
  }

  /** 初始化事件和处理方法 */
  public static void initEventReceiverInvokeMap() {
    logger.info("开始初始化事件和处理方案映射表");
    ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
    Map<String, Object> beansWithAnnotation =
        applicationContext.getBeansWithAnnotation(HandlerClass.class);
    Set<Map.Entry<String, Object>> entries = beansWithAnnotation.entrySet();
    for (Map.Entry<String, Object> entry : entries) {
      Class<?> aClass = entry.getValue().getClass();
      Method[] declaredMethods = aClass.getDeclaredMethods();
      for (Method method : declaredMethods) {
        if (method.isAnnotationPresent(ReceiverHandler.class)) {
          ReceiverInvoke receiverInvoke = new ReceiverInvoke(entry.getValue(), method);
          // 初始化指令和消息映射关系
          Parameter[] parameters = method.getParameters();
          Class<? extends Event> clazz = null;
          try {
            clazz = (Class<? extends Event>) parameters[0].getType();
          } catch (ClassCastException e) {
            logger.error("Receiver[{}]处理方法参数有误，应该实现[{}]接口", entry.getValue(), Event.class);
          }
          EventBusManager.registerReceiverInvoke(clazz, receiverInvoke);
        }
      }
    }
    logger.info("完成初始化事件和处理方案映射表");
  }
}
