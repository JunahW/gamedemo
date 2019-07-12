package com.example.gamedemo.server.game.buff.packet;

/**
 * @author wengj
 * @description
 * @date 2019/7/12
 */
public class SM_AddBuff {
  long targetId;

  int buffId;

  public static SM_AddBuff valueOf(long targerId, int buffId) {
    SM_AddBuff sm_addBuff = new SM_AddBuff();
    sm_addBuff.setTargetId(targerId);
    sm_addBuff.setBuffId(buffId);
    return sm_addBuff;
  }

  public long getTargetId() {
    return targetId;
  }

  public void setTargetId(long targetId) {
    this.targetId = targetId;
  }

  public int getBuffId() {
    return buffId;
  }

  public void setBuffId(int buffId) {
    this.buffId = buffId;
  }
}
