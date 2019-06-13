package com.example.gamedemo.server.game.equip.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.bag.model.Consume;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @author wengj
 * @description：装备升阶消耗资源
 * @date 2019/6/5
 */
@Resource
public class EquipEnhanceResource implements ResourceInterface {

  /** 唯一id */
  @ExcelColumn(columnName = "enhanceId")
  private int enhanceId;

  /** 等级 */
  @ExcelColumn(columnName = "position")
  private int position;

  /** 等级 */
  @ExcelColumn(columnName = "level")
  private int level;

  /** 消耗的道具json格式 */
  @ExcelColumn(columnName = "consumes")
  private String consumes;

  /** 增强的属性 */
  @ExcelColumn(columnName = "enhanceAttrs")
  private String enhanceAttr;

  /** 消耗物集合 */
  private List<Consume> consumeList;

  /** 增强属性集合 */
  private List<Attribute> attributeList;

  @Override
  public Object getId() {
    return this.enhanceId;
  }

  @Override
  public void postInit() {
    // 将json字符串装换为对象
    setConsumeList(JsonUtils.getListByString(getConsumes(), new TypeReference<List<Consume>>() {}));
    setAttributeList(
        JsonUtils.getListByString(getEnhanceAttr(), new TypeReference<List<Attribute>>() {}));
  }

  public int getEnhanceId() {
    return enhanceId;
  }

  public void setEnhanceId(int enhanceId) {
    this.enhanceId = enhanceId;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public String getConsumes() {
    return consumes;
  }

  public void setConsumes(String consumes) {
    this.consumes = consumes;
  }

  public String getEnhanceAttr() {
    return enhanceAttr;
  }

  public void setEnhanceAttr(String enhanceAttr) {
    this.enhanceAttr = enhanceAttr;
  }

  public List<Consume> getConsumeList() {
    return consumeList;
  }

  public void setConsumeList(List<Consume> consumeList) {
    this.consumeList = consumeList;
  }

  public List<Attribute> getAttributeList() {
    return attributeList;
  }

  public void setAttributeList(List<Attribute> attributeList) {
    this.attributeList = attributeList;
  }
}
