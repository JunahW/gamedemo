package com.example.gamedemo.server.game.guild.packet;

/**
 * @author wengj
 * @description
 * @date 2019/7/18
 */
public class CM_HandleApply {
  private Long id;

  /** 是否同意加入 */
  private boolean agree;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isAgree() {
    return agree;
  }

  public void setAgree(boolean agree) {
    this.agree = agree;
  }
}
