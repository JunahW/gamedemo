package com.example.gamedemo.common.dispatcher;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description：初始化指令的映射
 * @date 2019/6/13
 */
@Component
public class DispatcherApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    ControllerManager.initControllerMap();
  }
}
