package com.example.gamedemo.server.game.role.entity;

import com.example.gamedemo.common.ramcache.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wengj
 * @description
 * @date 2019/5/27
 */
@javax.persistence.Entity
@Table
public class RolePlusEnt implements Entity<String> {

    @Id
    private String roleId;

    @Column
    private String roleName;

    @Override
    public String getId() {
        return this.roleId;
    }

    @Override
    public void setNullId() {

    }

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

    @Override
    public boolean doSerialize() {
        return false;
    }

    @Override
    public boolean doDeSerialize() {
        return false;
    }
}
