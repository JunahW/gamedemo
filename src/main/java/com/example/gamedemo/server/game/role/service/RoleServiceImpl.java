package com.example.gamedemo.server.game.role.service;

import com.example.gamedemo.common.executer.WorkThreadPool;
import com.example.gamedemo.server.game.role.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/4/29
 */
@Component
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public void saveRole(Role role) {
        logger.info("开始执行");
        WorkThreadPool.singleThreadSchedule(5000, new Runnable() {
            @Override
            public void run() {
                logger.info("执行完毕");
            }
        });
    }
}
