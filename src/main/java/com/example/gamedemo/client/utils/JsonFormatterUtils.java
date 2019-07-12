package com.example.gamedemo.client.utils;

import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.scene.packet.SM_Aoi;

/**
 * @author wengj
 * @description:json格式化工具
 * @date 2019/6/19
 */
public class JsonFormatterUtils {
  /**
   * 打印输入到控制台
   *
   * @param jsonStr
   */
  public static void printJson(String jsonStr) {
    System.out.println(formatJson(jsonStr));
  }

  /**
   * 格式化
   *
   * @param jsonStr
   * @return
   */
  public static String formatJson(String jsonStr) {
    if (null == jsonStr || "".equals(jsonStr)) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    char last = '\0';
    char current = '\0';
    int indent = 0;
    boolean isInQuotationMarks = false;
    for (int i = 0; i < jsonStr.length(); i++) {
      last = current;
      current = jsonStr.charAt(i);
      switch (current) {
        case '"':
          if (last != '\\') {
            isInQuotationMarks = !isInQuotationMarks;
          }
          sb.append(current);
          break;

        case '{':
        case '[':
          sb.append(current);
          if (!isInQuotationMarks) {
            sb.append('\n');
            indent++;
            addIndentBlank(sb, indent);
          }
          break;

        case '}':
        case ']':
          if (!isInQuotationMarks) {
            sb.append('\n');
            indent--;
            addIndentBlank(sb, indent);
          }
          sb.append(current);
          break;

        case ',':
          sb.append(current);
          if (last != '\\' && !isInQuotationMarks) {
            sb.append('\n');
            addIndentBlank(sb, indent);
          }
          break;

        default:
          sb.append(current);
      }
    }
    return sb.toString();
  }

  /**
   * 添加space
   *
   * @param sb
   * @param indent
   */
  private static void addIndentBlank(StringBuilder sb, int indent) {
    for (int i = 0; i < indent; i++) {
      sb.append('\t');
    }
  }

  public static void main(String[] args) {
    printJson(
        "{\"size\":50,\"abstractItems\":[{\"@class\":\"com.example.gamedemo.server.game.bag.model.CommonItem\",\"objectId\":248866250028093440,\"itemResourceId\":2001,\"itemName\":\"铁矿石\",\"quantity\":5},{\"@class\":\"com.example.gamedemo.server.game.bag.model.CommonItem\",\"objectId\":248905471958847488,\"itemResourceId\":1001,\"itemName\":\"普通道具1\",\"quantity\":1},{\"@class\":\"com.example.gamedemo.server.game.bag.model.EquipItem\",\"objectId\":248905671507054592,\"itemResourceId\":1009,\"itemName\":\"绝世羽衣\",\"quantity\":1},{\"@class\":\"com.example.gamedemo.server.game.bag.model.EquipItem\",\"objectId\":248906011501531136,\"itemResourceId\":1009,\"itemName\":\"绝世羽衣\",\"quantity\":1},{\"@class\":\"com.example.gamedemo.server.game.bag.model.EquipItem\",\"objectId\":248906567209062400,\"itemResourceId\":1010,\"itemName\":\"极品战靴\",\"quantity\":1},null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null]}\n");
  }

  public static void printObjectJsonString(Object o) {
    String jsonString = JsonUtils.serializeEntity(o);
    System.out.println(formatJson(jsonString));
  }

  /**
   * 输出object
   *
   * @param object
   */
  public static void printObject(Object object) {
    if (object instanceof SM_Aoi) {
      System.out.println(object);
    } else {
      System.out.println(object);
    }
  }
}
