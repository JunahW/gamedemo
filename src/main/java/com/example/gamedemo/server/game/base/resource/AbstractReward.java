package com.example.gamedemo.server.game.base.resource;

/**
 * @author wengj
 * @description：奖励的抽象类
 * @date 2019/7/17
 */
public abstract class AbstractReward {
  /**
   * 转换字符串
   *
   * @param value
   */
  public abstract void doParse(String value);
}
