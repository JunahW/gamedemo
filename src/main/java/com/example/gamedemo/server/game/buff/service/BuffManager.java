package com.example.gamedemo.server.game.buff.service;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/6/25
 */
@Component
public class BuffManager {
  /** 静态资源 */
  private Map<Integer, BuffResource> buffResource =
      ResourceManager.getResourceMap(BuffResource.class);

  /**
   * 通过id获取buff配置资源
   *
   * @param id
   * @return
   */
  public BuffResource getBuffResourceById(Integer id) {
    return buffResource.get(id);
  }
}
