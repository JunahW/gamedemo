package com.example.gamedemo.server.game.role.service;

import com.example.gamedemo.server.game.role.entity.RoleEnt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/4/29
 */
@Component
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleManager roleManager;

    @Override
    public void saveRole(RoleEnt roleEnt) {
        logger.info("开始执行");
        roleManager.saveRole(roleEnt);
    }
}
