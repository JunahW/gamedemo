package com.example.gamedemo.common.executer.scene.impl;

import com.example.gamedemo.server.common.SpringContext;

import java.util.function.Consumer;

/**
 * @author wengj
 * @description
 * @date 2019/7/8
 */
public abstract class AbstractSceneAccountCommand<T> extends AbstractSceneCommand
    implements Runnable {
  /** 返回场景中执行的业务 */
  private Consumer<T> consumer;

  /** 执行的结果 */
  private T result;

  public AbstractSceneAccountCommand(int sceneId, Consumer<T> consumer) {
    super(sceneId);
    this.consumer = consumer;
  }

  @Override
  public void doAction() {
    consumer.accept(result);
  }

  @Override
  public void run() {
    doInAccountThread();
    // 在场景线程执行
    SpringContext.getSceneExecutorService().submit(this);
  }

  /** 在账户线程执行的业务 */
  public abstract void doInAccountThread();
}
