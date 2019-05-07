package com.example.gamedemo;

import com.example.gamedemo.server.MyServer;
import com.example.gamedemo.server.game.manager.ControllerManager;
import org.springframework.beans.factory.annotation.Autowired;
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
        new MyServer().start(args);
    }

}
