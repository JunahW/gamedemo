package com.example.gamedemo.common.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description:初始化事件的处理方法
 * @date 2019/6/13
 */
@Component
public class ReceiverApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    EventBusManager.initEventReceiverInvokeMap();
  }
}
