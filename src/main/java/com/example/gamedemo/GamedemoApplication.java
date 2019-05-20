package com.example.gamedemo;

import com.example.gamedemo.server.MyServer;
import com.example.gamedemo.server.SystemInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: wengj
 * @date: 2019/4/28
 * @description:
 */
@SpringBootApplication
public class GamedemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamedemoApplication.class, args);
        //初始化
        SystemInitializer.initControllerMap();
        SystemInitializer.initResource();
        new MyServer().start(args);
    }

}
