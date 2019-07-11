package com.example.gamedemo.server.game.skill.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.resource.ResourceInterface;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.common.model.Consume;
import com.example.gamedemo.server.game.attribute.Attribute;
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

  /** 增加的攻击力 */
  @ExcelColumn(columnName = "attack")
  private int attack;

  /** 消耗的道具json格式 */
  @ExcelColumn(columnName = "consumes")
  private String consumes;

  /** 消耗物集合 */
  private List<Consume> consumeList;

  @ExcelColumn(columnName = "beforeBuffs")
  private String beforeBuffs;

  /** 字符串buff用","隔开 */
  @ExcelColumn(columnName = "afterBuffs")
  private String afterBuffs;

  @ExcelColumn(columnName = "casterBeforeBuffs")
  private String casterBeforeBuffs;

  /** 字符串buff用","隔开 */
  @ExcelColumn(columnName = "casterAfterBuffs")
  private String casterAfterBuffs;

  /** buff数组 */
  private int[] afterBuffArray;

  /** buff数组 */
  private int[] beforeBuffArray;

  /** buff数组 */
  private int[] casterAfterBuffArray;

  /** buff数组 */
  private int[] casterBeforeBuffArray;

  /** 技能类型 */
  @ExcelColumn(columnName = "skillType")
  private int skillType;

  /** 攻击范围 */
  @ExcelColumn(columnName = "attackRadius")
  private int attackRadius;

  /** 技能释放中心 0 对方 1自身 */
  @ExcelColumn(columnName = "centerType")
  private int centerType;

  /** 技能范围类型 1 点 2 圆形 */
  @ExcelColumn(columnName = "skillAreaType")
  private int skillAreaType;

  /** 技能范围参数 */
  @ExcelColumn(columnName = "skillAreaParam")
  private String skillAreaParam;

  /** 技能释放范围类型 1 点 2 圆形 */
  @ExcelColumn(columnName = "areaType")
  private int areaType;

  /** 技能释放范围参数 */
  @ExcelColumn(columnName = "areaParam")
  private String areaParam;

  /** 最大目标数量 */
  @ExcelColumn(columnName = "targetMax")
  private int targetMax;

  /** 是否包括自身 */
  @ExcelColumn(columnName = "containSelf")
  private boolean containSelf;

  /** 影响 */
  @ExcelColumn(columnName = "effect")
  private String effect;

  private List<Attribute> effectList;

  @Override
  public Object getId() {
    return skillId;
  }

  @Override
  public void postInit() { // 将json字符串装换为对象
    setConsumeList(JsonUtils.getListByString(getConsumes(), new TypeReference<List<Consume>>() {}));
    if (afterBuffs != null) {
      String[] buffSplit = afterBuffs.split(SystemConstant.SPLIT_TOKEN_COMMA);
      int[] ints = new int[buffSplit.length];
      for (int i = 0; i < buffSplit.length; i++) {
        ints[i] = Integer.parseInt(buffSplit[i]);
      }
      setAfterBuffArray(ints);
    }

    if (beforeBuffs != null) {
      String[] buffSplit = beforeBuffs.split(SystemConstant.SPLIT_TOKEN_COMMA);
      int[] ints = new int[buffSplit.length];
      for (int i = 0; i < buffSplit.length; i++) {
        ints[i] = Integer.parseInt(buffSplit[i]);
      }
      setBeforeBuffArray(ints);
    }

    if (casterAfterBuffs != null) {
      String[] buffSplit = casterAfterBuffs.split(SystemConstant.SPLIT_TOKEN_COMMA);
      int[] ints = new int[buffSplit.length];
      for (int i = 0; i < buffSplit.length; i++) {
        ints[i] = Integer.parseInt(buffSplit[i]);
      }
      setCasterAfterBuffArray(ints);
    }

    if (casterBeforeBuffs != null) {
      String[] buffSplit = casterBeforeBuffs.split(SystemConstant.SPLIT_TOKEN_COMMA);
      int[] ints = new int[buffSplit.length];
      for (int i = 0; i < buffSplit.length; i++) {
        ints[i] = Integer.parseInt(buffSplit[i]);
      }
      setCasterBeforeBuffArray(ints);
    }

    if (effect != null) {
      setEffectList(
          JsonUtils.getListByString(getEffect(), new TypeReference<List<Attribute>>() {}));
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

  public String getAfterBuffs() {
    return afterBuffs;
  }

  public void setAfterBuffs(String afterBuffs) {
    this.afterBuffs = afterBuffs;
  }

  public int[] getAfterBuffArray() {
    return afterBuffArray;
  }

  public void setAfterBuffArray(int[] afterBuffArray) {
    this.afterBuffArray = afterBuffArray;
  }

  public int getSkillType() {
    return skillType;
  }

  public void setSkillType(int skillType) {
    this.skillType = skillType;
  }

  public int getAttackRadius() {
    return attackRadius;
  }

  public void setAttackRadius(int attackRadius) {
    this.attackRadius = attackRadius;
  }

  public int getAttack() {
    return attack;
  }

  public void setAttack(int attack) {
    this.attack = attack;
  }

  public int getCenterType() {
    return centerType;
  }

  public void setCenterType(int centerType) {
    this.centerType = centerType;
  }

  public int getAreaType() {
    return areaType;
  }

  public void setAreaType(int areaType) {
    this.areaType = areaType;
  }

  public String getAreaParam() {
    return areaParam;
  }

  public void setAreaParam(String areaParam) {
    this.areaParam = areaParam;
  }

  public int getTargetMax() {
    return targetMax;
  }

  public void setTargetMax(int targetMax) {
    this.targetMax = targetMax;
  }

  public boolean getContainSelf() {
    return containSelf;
  }

  public int getSkillAreaType() {
    return skillAreaType;
  }

  public void setSkillAreaType(int skillAreaType) {
    this.skillAreaType = skillAreaType;
  }

  public String getSkillAreaParam() {
    return skillAreaParam;
  }

  public void setSkillAreaParam(String skillAreaParam) {
    this.skillAreaParam = skillAreaParam;
  }

  public boolean isContainSelf() {
    return containSelf;
  }

  public void setContainSelf(boolean containSelf) {
    this.containSelf = containSelf;
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

  public String getBeforeBuffs() {
    return beforeBuffs;
  }

  public void setBeforeBuffs(String beforeBuffs) {
    this.beforeBuffs = beforeBuffs;
  }

  public int[] getBeforeBuffArray() {
    return beforeBuffArray;
  }

  public void setBeforeBuffArray(int[] beforeBuffArray) {
    this.beforeBuffArray = beforeBuffArray;
  }

  public String getCasterBeforeBuffs() {
    return casterBeforeBuffs;
  }

  public void setCasterBeforeBuffs(String casterBeforeBuffs) {
    this.casterBeforeBuffs = casterBeforeBuffs;
  }

  public String getCasterAfterBuffs() {
    return casterAfterBuffs;
  }

  public void setCasterAfterBuffs(String casterAfterBuffs) {
    this.casterAfterBuffs = casterAfterBuffs;
  }

  public int[] getCasterAfterBuffArray() {
    return casterAfterBuffArray;
  }

  public void setCasterAfterBuffArray(int[] casterAfterBuffArray) {
    this.casterAfterBuffArray = casterAfterBuffArray;
  }

  public int[] getCasterBeforeBuffArray() {
    return casterBeforeBuffArray;
  }

  public void setCasterBeforeBuffArray(int[] casterBeforeBuffArray) {
    this.casterBeforeBuffArray = casterBeforeBuffArray;
  }
}
