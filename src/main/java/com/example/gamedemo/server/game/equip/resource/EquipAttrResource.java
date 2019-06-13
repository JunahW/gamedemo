package com.example.gamedemo.server.game.equip.resource;

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
 * @date 2019/6/3
 */
@Resource
public class EquipAttrResource implements ResourceInterface {

  /** 配置表唯一id */
  @ExcelColumn(columnName = "itemId")
  private int itemId;

  /** 基础属性字符串 */
  @ExcelColumn(columnName = "basicAttributeString")
  private String basicAttributeString;

  /** 将基础字符串转换为集合 */
  private List<Attribute> attributes;

  @Override
  public Object getId() {
    return this.itemId;
  }

  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public String getBasicAttributeString() {
    return basicAttributeString;
  }

  public void setBasicAttributeString(String basicAttributeString) {
    this.basicAttributeString = basicAttributeString;
  }

  /**
   * 将字符串的json转换为对象
   *
   * @return
   */
  public List<Attribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<Attribute> attributes) {
    this.attributes = attributes;
  }

  @Override
  public void postInit() {
    if (attributes == null) {
      attributes =
          JsonUtils.getListByString(
              this.basicAttributeString, new TypeReference<List<Attribute>>() {});
    }
  }
}
