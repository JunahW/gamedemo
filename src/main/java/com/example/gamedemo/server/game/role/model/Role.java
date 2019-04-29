package com.example.gamedemo.server.game.role.model;

import com.example.gamedemo.server.game.scene.model.Scene;

/**
 * @author wengj
 * @description
 * @date 2019/4/29
 */
public class Role {

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色状态 0 死亡  1 生存
     */
    private int status=1;

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

    public int getStatus() {
        return status;
    }


    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
