package com.example.gamedemo.server.game.skill.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.resource.ResourceInterface;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.common.model.Consume;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/6/20
 */
@Resource
public class SkillResource implements ResourceInterface {
  /** 技能id */
  @ExcelColumn(columnName = "skillId")
  private int skillId;

  /** 技能名称 */
  @ExcelColumn(columnName = "skillName")
  private String skillName;

  /** 冷却时间毫秒 */
  @ExcelColumn(columnName = "cd")
  private int cd;

  /** 消耗的魔法 */
  @ExcelColumn(columnName = "mp")
  private int mp;

  /** 消耗的道具json格式 */
  @ExcelColumn(columnName = "consumes")
  private String consumes;

  /** 消耗物集合 */
  private List<Consume> consumeList;
  /** 字符串buff用","隔开 */
  private String buffs;
  /** buff数组 */
  private int[] buffArray;

  @Override
  public Object getId() {
    return skillId;
  }

  @Override
  public void postInit() { // 将json字符串装换为对象
    setConsumeList(JsonUtils.getListByString(getConsumes(), new TypeReference<List<Consume>>() {}));
    if (buffs != null) {
      String[] buffSplit = buffs.split(SystemConstant.SPLIT_TOKEN_COMMA);
      int[] ints = new int[buffSplit.length];
      for (int i = 0; i < buffSplit.length; i++) {
        ints[i] = Integer.parseInt(buffSplit[i]);
      }
      setBuffArray(ints);
    }
  }

  public int getSkillId() {
    return skillId;
  }

  public void setSkillId(int skillId) {
    this.skillId = skillId;
  }

  public String getSkillName() {
    return skillName;
  }

  public void setSkillName(String skillName) {
    this.skillName = skillName;
  }

  public int getCd() {
    return cd;
  }

  public void setCd(int cd) {
    this.cd = cd;
  }

  public int getMp() {
    return mp;
  }

  public void setMp(int mp) {
    this.mp = mp;
  }

  public String getConsumes() {
    return consumes;
  }

  public void setConsumes(String consumes) {
    this.consumes = consumes;
  }

  public List<Consume> getConsumeList() {
    return consumeList;
  }

  public void setConsumeList(List<Consume> consumeList) {
    this.consumeList = consumeList;
  }

  public String getBuffs() {
    return buffs;
  }

  public void setBuffs(String buffs) {
    this.buffs = buffs;
  }

  public int[] getBuffArray() {
    return buffArray;
  }

  public void setBuffArray(int[] buffArray) {
    this.buffArray = buffArray;
  }
}
