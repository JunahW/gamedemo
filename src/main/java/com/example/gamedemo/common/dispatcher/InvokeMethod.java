package com.example.gamedemo.common.dispatcher;

import com.example.gamedemo.common.executer.common.CommonExecutor;
import com.example.gamedemo.common.executer.scene.SceneExecutor;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.packet.CM_CreateAccount;
import com.example.gamedemo.server.game.account.packet.CM_LoginAccount;
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
    Account account = new Account();
    if (packet instanceof CM_CreateAccount) {
      CM_CreateAccount cm_createAccount = (CM_CreateAccount) packet;
      account.setAccountId(cm_createAccount.getAccountId());
    } else if (packet instanceof CM_LoginAccount) {
      CM_LoginAccount cm_loginAccount = (CM_LoginAccount) packet;
      account.setAccountId(cm_loginAccount.getAccountId());
    } else {
      account = session.getAccount();
    }

    // 未选择角色就在用户线程
    if (session.getPlayer() == null) {
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
