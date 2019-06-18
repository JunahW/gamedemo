package com.example.gamedemo.common.dispatcher;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.utils.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: wengj
 * @date: 2019/5/7
 * @description: 请求和处理方法映射表
 */
public class ControllerManager {
  private static final Logger logger = LoggerFactory.getLogger(ControllerManager.class);

  /** 请求和处理之间的映射 */
  private static final Map<String, InvokeMethod> CONTROLLER_MAP = new HashMap<>();

  /** 请求和消息之间的映射 */
  private static final Map<String, Class> CMD_PACKER_MAP = new HashMap<>();

  /**
   * @param cmd
   * @param invokeMethod
   */
  public static void add(String cmd, InvokeMethod invokeMethod) {
    if (CONTROLLER_MAP.containsKey(cmd)) {
      logger.error("请求指令{}重复", cmd);
      throw new RuntimeException();
    } else {
      CONTROLLER_MAP.put(cmd, invokeMethod);
    }
  }

  /**
   * 获取指令的处理方法
   *
   * @param cmd
   * @return
   */
  public static InvokeMethod get(String cmd) {
    // 通过int的msgId找到枚举的MsgId
    return CONTROLLER_MAP.get(cmd);
  }

  /**
   * 新增指令消息关系
   *
   * @param cmd
   * @param clazz
   */
  public static void addPacket(String cmd, Class clazz) {
    if (CMD_PACKER_MAP.containsKey(cmd)) {
      logger.error("请求指令{}重复", cmd);
      throw new RuntimeException();
    } else {
      CMD_PACKER_MAP.put(cmd, clazz);
    }
  }

  /**
   * 获取指令的对应的消息
   *
   * @param cmd
   * @return
   */
  public static Class getClassByCmd(String cmd) {
    // 通过int的msgId找到枚举的MsgId
    return CMD_PACKER_MAP.get(cmd);
  }

  /** 初始化指令和处理方法映射表 */
  public static void initControllerMap() {
    logger.info("开始初始化指令和处理方案映射表");
    ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
    Map<String, Object> beansWithAnnotation =
        applicationContext.getBeansWithAnnotation(HandlerClass.class);
    Set<Map.Entry<String, Object>> entries = beansWithAnnotation.entrySet();
    for (Map.Entry<String, Object> entry : entries) {
      Class<?> aClass = entry.getValue().getClass();
      Method[] declaredMethods = aClass.getDeclaredMethods();
      for (Method method : declaredMethods) {
        if (method.isAnnotationPresent(HandlerMethod.class)) {
          HandlerMethod annotation = method.getAnnotation(HandlerMethod.class);
          String cmd = annotation.cmd();
          InvokeMethod invokeMethod = new InvokeMethod(entry.getValue(), method);
          ControllerManager.add(cmd, invokeMethod);
          // 初始化指令和消息映射关系
          Parameter[] parameters = method.getParameters();
          ControllerManager.addPacket(cmd, parameters[1].getType());
        }
      }
    }
    logger.info("完成初始化指令和处理方案映射表");
  }
}
