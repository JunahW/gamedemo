package com.example.gamedemo.server.game.role.model;

import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.model.SceneObject;

/**
 * @author wengj
 * @description
 * @date 2019/4/29
 */
public class Role extends SceneObject {

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;


    /**
     * 所在场景
     */
    private Scene scene;

    public String getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
