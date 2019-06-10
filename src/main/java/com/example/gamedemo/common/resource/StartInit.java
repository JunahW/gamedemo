package com.example.gamedemo.common.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Order(value = 1)
public class StartInit implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(StartInit.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
