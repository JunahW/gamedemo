package com.example.gamedemo.server.game.guild.packet;

import com.example.gamedemo.server.game.guild.constant.PositionTypeEnum;

import java.util.Map;

/**
 * @author wengj
 * @description：成员列表
 * @date 2019/7/19
 */
public class SM_MemberList {
  private Map<Long, PositionTypeEnum> members;

  /**
   * @param members
   * @return
   */
  public static SM_MemberList valueOf(Map<Long, PositionTypeEnum> members) {
    SM_MemberList sm_memberList = new SM_MemberList();
    sm_memberList.setMembers(members);
    return sm_memberList;
  }

  public Map<Long, PositionTypeEnum> getMembers() {
    return members;
  }

  public void setMembers(Map<Long, PositionTypeEnum> members) {
    this.members = members;
  }
}
