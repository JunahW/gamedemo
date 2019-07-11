package com.example.gamedemo.server.common.service;

import com.example.gamedemo.server.common.SpringContext;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/7/11
 */
@Component
public class GlobalService {

  public void onStart() throws Exception {
    SpringContext.getSceneService().sceneStart();
  }

  public void onStop() throws Exception {}
}
