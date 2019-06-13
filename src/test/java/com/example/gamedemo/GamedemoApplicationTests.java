package com.example.gamedemo;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.dispatcher.ControllerManager;
import com.example.gamedemo.common.dispatcher.InvokeMethod;
import com.example.gamedemo.common.utils.ApplicationContextProvider;
import com.example.gamedemo.server.game.account.service.AccountService;
import com.example.gamedemo.server.game.player.packet.CM_CreatePlayer;
import com.example.gamedemo.server.game.scene.resource.SceneResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GamedemoApplicationTests {

  @Autowired ApplicationContext applicationContext;
  @Autowired private AccountService accountService;

  @Test
  public void contextLoads() {}

  @Test
  public void testClass() {
    Class<SceneResource> sceneClass = SceneResource.class;
    System.out.println(sceneClass.getSimpleName());
  }

  @Test
  public void testApplication() {
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
        }
      }
    }
  }

  @Test
  public void testArray() {
    int[][] a = {{1, 1}, {2, 2}};
    System.out.println(a);
  }

  @Test
  public void testHandlerAnno() {
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
          Parameter[] parameters = method.getParameters();
          ControllerManager.addPacket(cmd, parameters[1].getType());
          System.out.println(ControllerManager.getClassByCmd(cmd));
        }
      }
    }
    System.out.println(ControllerManager.get("test"));
  }

  @Test
  public void testReflectPropertyOrder() {
    Class<CM_CreatePlayer> accountClass = CM_CreatePlayer.class;
    Field[] declaredFields = accountClass.getDeclaredFields();
    for (Field field : declaredFields) {
      System.out.println(field.getName());
    }
  }
}
