package com.example.gamedemo.server.game.player.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;

/**
 * @author wengj
 * @description
 * @date 2019/5/31
 */
@Resource
public class PlayerResource implements ResourceInterface {
  @ExcelColumn(columnName = "roleId")
  private int roleId;

  @ExcelColumn(columnName = "roleName")
  private String roleName;

  public int getRoleId() {
    return roleId;
  }

  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  @Override
  public Object getId() {
    return roleId;
  }

  @Override
  public void postInit() {}
}
