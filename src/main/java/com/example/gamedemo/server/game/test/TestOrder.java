package com.example.gamedemo.server.game.test;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/6/6
 */
@Component
@Order(value = 5)
public class TestOrder implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("===============init order 1==============");
    }
}
