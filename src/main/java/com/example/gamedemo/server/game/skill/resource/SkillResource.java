package com.example.gamedemo.server.game.skill.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;

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

  @Override
  public Object getId() {
    return skillId;
  }

  @Override
  public void postInit() {}

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
}
