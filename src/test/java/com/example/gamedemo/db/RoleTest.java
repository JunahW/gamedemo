package com.example.gamedemo.db;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.server.game.role.entity.RoleEnt;
import com.example.gamedemo.server.game.role.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wengj
 * @description
 * @date 2019/5/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private Accessor accessor;

    @Test
    public void testCache() {
        RoleEnt roleEnt = new RoleEnt();
        roleEnt.setRoleId("r1003");
        roleEnt.setRoleName("name1001");

        roleService.saveRole(roleEnt);
    }

    @Test
    public void testDao() {
        RoleEnt roleEnt = new RoleEnt();
        roleEnt.setRoleId("r1001");
        roleEnt.setRoleName("name1001");
        accessor.save(RoleEnt.class, roleEnt);
    }


}
