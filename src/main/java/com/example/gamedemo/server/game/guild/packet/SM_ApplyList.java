package com.example.gamedemo.server.game.guild.packet;

import java.util.Set;

/**
 * @author wengj
 * @description
 * @date 2019/7/18
 */
public class SM_ApplyList {
  /** 申请列表 */
  private Set<Long> applyList;

  /**
   * @param applyList
   * @return
   */
  public static SM_ApplyList valueOf(Set<Long> applyList) {
    SM_ApplyList sm_applyList = new SM_ApplyList();
    sm_applyList.setApplyList(applyList);
    return sm_applyList;
  }

  public Set<Long> getApplyList() {
    return applyList;
  }

  public void setApplyList(Set<Long> applyList) {
    this.applyList = applyList;
  }
}
