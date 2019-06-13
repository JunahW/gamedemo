package com.example.gamedemo;

import com.example.gamedemo.common.dispatcher.ControllerManager;
import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.server.MyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author: wengj
 * @date: 2019/4/28
 * @description:
 */
@SpringBootApplication
@EntityScan("com.example.gamedemo.server.game")
public class GamedemoApplication {

  public static void main(String[] args) {

    SpringApplication.run(GamedemoApplication.class, args);
    // 初始化
    // SystemInitializer.initResource();
    ControllerManager.initControllerMap();
    EventBusManager.initEventReceiverInvokeMap();

    new MyServer().start(args);
  }
}
