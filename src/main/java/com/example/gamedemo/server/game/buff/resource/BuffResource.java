package com.example.gamedemo.server.game.buff.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
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
  @ExcelColumn(columnName = "buffId")
  private int buffId;

  /** buff名称 */
  @ExcelColumn(columnName = "buffName")
  private String buffName;

  /** buff类型 */
  @ExcelColumn(columnName = "buffType")
  private int buffType;

  /** buff的持续时间毫秒值 */
  @ExcelColumn(columnName = "duration")
  private int duration;

  /** 执行周期 */
  @ExcelColumn(columnName = "period")
  private int period;

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

  public int getPeriod() {
    return period;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

  @Override
  public Object getId() {
    return buffId;
  }

  @Override
  public void postInit() {}
}
