package com.example.gamedemo.server.game.role.service;

import com.example.gamedemo.common.ramcache.service.EntityCacheService;
import com.example.gamedemo.server.game.role.entity.RoleEnt;
import com.example.gamedemo.server.game.role.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wengj
 * @description
 * @date 2019/4/29
 */
@Component
public class RoleManager {


    @Autowired
    private EntityCacheService<String, RoleEnt> entEntityCacheService;

    /**
     * 角色map
     */
    public static ConcurrentHashMap<String, Role> roleId2RoleMap = new ConcurrentHashMap<String, Role>();

    public void saveRole(RoleEnt roleEnt) {
        entEntityCacheService.writeBack(roleEnt.getId(), roleEnt);
    }

    public RoleEnt getRole() {
        return null;
    }


}
