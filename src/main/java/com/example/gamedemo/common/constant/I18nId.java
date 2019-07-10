package com.example.gamedemo.common.constant;

/**
 * @author: wengj
 * @date: 2019/5/27
 * @description:国际化id
 */
public interface I18nId {
  /** 错误 */
  int ERROR = 10000;

  /** 成功 */
  int SUCCESS = 10001;

  /** 失败 */
  int FAIL = 10002;

  /** 请求参数不合法 */
  int REQUEST_PARAMETER_ILLEGAL = 10003;
  /** ==========================账户相关=========================* */
  /** 用户已存在 */
  int ACCOUNT_EXIST = 20001;

  /** 账户不存在 */
  int ACCOUNT_NO_EXIST = 20002;

  /** ==========================玩家相关=========================* */
  /** 玩家已存在 */
  int PLAYER_EXIST = 30000;

  /** 玩家不存在 */
  int PLAYER_NO_EXIST = 30001;

  /** ==========================道具相关=========================* */
  /** 道具不存在 */
  int ITEM_NO_EXIST = 40000;

  /** 背包不存在该物品 */
  int BAG_NO_EXIST_ITEM = 40001;

  /** 道具数量不足 */
  int ITEM_QUANTITY_NO_ENOUGH = 40002;

  /** 道具扣除失败 */
  int ITEM_REDUCE_FAIL = 40003;

  /** ==========================装备相关=========================* */

  /** 装备不存在 */
  int EQUIPMENT_NO_EXIST = 50000;

  /** 该道具不是装备类型 */
  int ITEM_NO_EQUIPMENT = 50001;

  /** 玩家职业和道具类型不匹配 */
  int PLAYER_TYPE_NO_MATCH_EQUIPMENT = 50002;

  /** 该部位不存在装备 */
  int POSITION_NO_EXIST_EQUIPMENT = 50003;

  /** 该装备部位没定义 */
  int POSITION_NO_DEFINE = 50004;

  /** 装备栏卡槽位置参数不合法 */
  int SLOT_POSITION_ILLEGAL = 50005;

  /** 装备栏该卡槽已升满级 */
  int SLOT_LEVEL_CELL = 50006;

  /** 卡槽升级所需道具不足 */
  int SLOT_UP_ITEM_NO_ENOUGH = 50007;

  /** ==========================场景相关=========================* */

  /** 场景不存在 */
  int SCENE_NO_EXIST = 60000;

  /** 场景不相邻 */
  int SCENE_NO_NEIGHBOR = 60001;

  /** 移动的坐标参数有误 */
  int SCENE_POSITION_ERROR = 60002;

  /** 场景位置不可走 */
  int SCENE_OBSTACLE = 60003;

  /** 场景不存在 */
  int SCENE_RESOURCE_NO_EXIST = 60004;

  /** ==========================技能相关=========================* */

  /** 技能还未学习 */
  int SKILL_NO_STUDY = 70000;

  /** 技能的位置有误 */
  int SKILL_INDEX_ERROR = 70001;

  /** 技能不存在 */
  int SKILL_NO_EXIST = 70002;

  /** 学习技能道具不足 */
  int SKILL_NO_ENOUGH_ITEM = 70003;

  /** 魔法值不足 */
  int MP_IS_NO_ENOUGH = 70004;

  /** 目标物不是creature */
  int TARGET_NO_CREATURE = 70005;

  /** 技能还未cd完成 */
  int SKILL_NO_CD_YET = 70006;

  /** 技能已经学习 */
  int SKILL_STUDIED = 70007;

  /** ==========================战斗相关=========================* */

  /** 找不到目标物 */
  int TARGET_NO_FOUND = 80000;
}
