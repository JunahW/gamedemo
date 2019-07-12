package com.example.gamedemo.client.dispatcher;

/**
 * @author wengj
 * @description 请求对应的方法
 * @date 2019/5/7
 */
public interface IHandler {
  /**
   * 处理信息
   *
   * @param message
   */
  void handleMessage(Object message);
}
