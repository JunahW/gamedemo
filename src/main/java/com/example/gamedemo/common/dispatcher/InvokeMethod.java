package com.example.gamedemo.common.dispatcher;

import com.example.gamedemo.common.executer.scene.SceneExecutor;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
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
    // 未选择角色就在用户线程
    if (session.getPlayer() == null) {
      String accountId = getAccountId(session, packet);
      SpringContext.getAccountExecutorService()
          .addTask(accountId, new IoHandleEvent(session, packet, this));
    } else {
      int sceneId = session.getPlayer().getSceneId();
      SceneExecutor.addTask(sceneId, new IoHandleEvent(session, packet, this));
    }
    return null;
  }

  /**
   * 获取accountId
   *
   * @param session
   * @param packet
   * @return
   */
  private String getAccountId(TSession session, Object packet) {
    String accountId = null;
    if (packet instanceof CM_CreateAccount) {
      CM_CreateAccount cm_createAccount = (CM_CreateAccount) packet;
      accountId = cm_createAccount.getAccountId();
    } else if (packet instanceof CM_LoginAccount) {
      CM_LoginAccount cm_loginAccount = (CM_LoginAccount) packet;
      accountId = cm_loginAccount.getAccountId();
    } else {
      accountId = session.getAccount().getAccountId();
    }
    return accountId;
  }

  public final class IoHandleEvent implements Runnable {
    private final TSession session;
    private final Object packet;
    private final InvokeMethod invokeMethod;

    public IoHandleEvent(TSession session, Object packet, InvokeMethod invokeMethod) {
      this.session = session;
      this.packet = packet;
      this.invokeMethod = invokeMethod;
    }

    @Override
    public void run() {
      ReflectionUtils.invokeMethod(
          invokeMethod.getMethod(), invokeMethod.getObject(), session, packet);
    }
  }
}
