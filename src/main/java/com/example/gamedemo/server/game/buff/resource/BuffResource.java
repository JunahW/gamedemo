package com.example.gamedemo.server.game.buff.resource;

import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;

/**
 * @author wengj
 * @description
 * @date 2019/6/25
 */
@Resource
public class BuffResource implements ResourceInterface {
  /** buff配置id */
  private int buffId;

  /** buff名称 */
  private String buffName;

  /** buff类型 */
  private int buffType;

  /** buff的持续时间毫秒值 */
  private int duration;

  public int getBuffId() {
    return buffId;
  }

  public void setBuffId(int buffId) {
    this.buffId = buffId;
  }

  public String getBuffName() {
    return buffName;
  }

  public void setBuffName(String buffName) {
    this.buffName = buffName;
  }

  public int getBuffType() {
    return buffType;
  }

  public void setBuffType(int buffType) {
    this.buffType = buffType;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  @Override
  public Object getId() {
    return buffId;
  }

  @Override
  public void postInit() {}
}
