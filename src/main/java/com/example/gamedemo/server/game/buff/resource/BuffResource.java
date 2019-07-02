package com.example.gamedemo.server.game.buff.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

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

  /** 目标对象 1 自身 0 对方 */
  @ExcelColumn(columnName = "target")
  private int target;

  /** buff影响的属性 */
  @ExcelColumn(columnName = "effect")
  private String effect;

  private List<Attribute> effectList;

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

  public int getTarget() {
    return target;
  }

  public void setTarget(int target) {
    this.target = target;
  }

  public String getEffect() {
    return effect;
  }

  public void setEffect(String effect) {
    this.effect = effect;
  }

  public List<Attribute> getEffectList() {
    return effectList;
  }

  public void setEffectList(List<Attribute> effectList) {
    this.effectList = effectList;
  }

  @Override
  public Object getId() {
    return buffId;
  }

  @Override
  public void postInit() {
    effectList = JsonUtils.getListByString(this.effect, new TypeReference<List<Attribute>>() {});
  }
}
