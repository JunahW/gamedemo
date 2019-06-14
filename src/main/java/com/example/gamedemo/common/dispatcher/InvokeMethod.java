package com.example.gamedemo.common.dispatcher;

import com.example.gamedemo.common.executer.common.CommonExecutor;
import com.example.gamedemo.common.executer.scene.SceneExecutor;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.game.account.model.Account;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author wengj
 * @description 请求对应的方法
 * @date 2019/5/7
 */
public class InvokeMethod {
  /** 执行对象 */
  private Object object;
  /** 执行方法 */
  private Method method;

  public InvokeMethod() {}

  public InvokeMethod(Object object, Method method) {
    this.object = object;
    this.method = method;
  }

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  /**
   * 执行请求
   *
   * @param session
   * @param packet
   * @return
   */
  public Object invoke(TSession session, Object packet) {
    // TODO 线程池
    Account account = session.getAccount();
    if (account == null) {
      // 未登录时，默认使用的线程
      CommonExecutor.COMMON_SERVICE[0].submit(
          new Runnable() {
            @Override
            public void run() {
              ReflectionUtils.invokeMethod(method, object, session, packet);
            }
          });
    } else if (session.getPlayer() == null) {
      String accountId = account.getAccountId();
      int index = CommonExecutor.modeIndex(accountId);
      CommonExecutor.COMMON_SERVICE[index].submit(
          new Runnable() {
            @Override
            public void run() {
              ReflectionUtils.invokeMethod(method, object, session, packet);
            }
          });
    } else {
      int sceneId = session.getPlayer().getSceneId();
      int modeIndex = SceneExecutor.modeIndex(sceneId);
      SceneExecutor.SCENE_SERVICE[modeIndex].submit(
          new Runnable() {
            @Override
            public void run() {
              ReflectionUtils.invokeMethod(method, object, session, packet);
            }
          });
    }
    return null;
  }
}
