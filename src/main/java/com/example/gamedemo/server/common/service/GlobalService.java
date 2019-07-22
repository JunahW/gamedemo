package com.example.gamedemo.server.common.service;

import com.example.gamedemo.server.common.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/7/11
 */
@Component
public class GlobalService {
  private static final Logger logger = LoggerFactory.getLogger(GlobalService.class);

  public void onStart() throws Exception {
    SpringContext.getSceneService().sceneStart();
    logger.info("场景定时器启动完成");
    SpringContext.getRankService().initRanks();
    logger.info("预加载排行板完成");
  }

  public void onStop() throws Exception {}
}
