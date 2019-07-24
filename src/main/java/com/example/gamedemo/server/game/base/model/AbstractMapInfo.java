package com.example.gamedemo.server.game.base.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author wengj
 * @description:抽象的副本信息
 * @date 2019/7/23
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = false)
public abstract class AbstractMapInfo {

  /**
   * 创建
   *
   * @return
   */
  public abstract <T extends AbstractMapInfo> T valueOf();
}
