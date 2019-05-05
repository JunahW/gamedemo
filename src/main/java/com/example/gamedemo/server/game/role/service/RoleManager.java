package com.example.gamedemo.server.game.role.service;

import com.example.gamedemo.server.game.role.model.Role;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wengj
 * @description
 * @date 2019/4/29
 */
@Component
public class RoleManager {

    /**
     * 角色map
     */
    public static ConcurrentHashMap<String, Role> roleId2RoleMap = new ConcurrentHashMap<String, Role>();


}
