package com.example.gamedemo.server.game.guild.constant;

/**
 * @author: wengj
 * @date: 2019/7/18
 * @description: 行会成员类型
 */
public enum PositionTypeEnum {
  /** 会长 */
  PRESIDENT(1, "会长"),

  /** 副会长 */
  VICE_PRESIDENT(2, "副会长"),

  /** 普通成员 */
  MEMBER(3, "成员");

  private int typeId;

  private String title;

  PositionTypeEnum(int typeId, String title) {
    this.typeId = typeId;
    this.title = title;
  }

  public int getTypeId() {
    return typeId;
  }

  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
