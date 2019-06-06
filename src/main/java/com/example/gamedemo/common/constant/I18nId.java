package com.example.gamedemo.common.constant;

/**
 * @author: wengj
 * @date: 2019/5/27
 * @description:国际化id
 */
public interface I18nId {
    //错误
    int ERROR = 10000;
    //成功
    int SUCCESS = 10001;
    //失败
    int FAIL = 10002;
    //请求参数不合法
    int REQUEST_PARAMETER_ILLEGAL = 10003;
    /**==========================账户相关=========================**/
    /**
     * 用户已存在
     */
    int ACCOUNT_EXIST = 20001;

    /**
     * 账户不存在
     */
    int ACCOUNT_NO_EXIST = 20002;


    /**==========================玩家相关=========================**/
    /**
     * 玩家已存在
     */
    int PLAYER_EXIST = 30000;

    /**
     * 玩家不存在
     */
    int PLAYER_NO_EXIST = 30001;


    /**==========================道具相关=========================**/
    /**
     * 道具不存在
     */
    int ITEM_NO_EXIST = 40000;

    /**
     * 背包不存在该物品
     */
    int BAG_NO_EXIST_ITEM = 40001;

    /**
     * 道具数量不足
     */
    int ITEM_QUANTITY_NO_ENOUGH = 40002;

    /**==========================装备相关=========================**/

    /**
     * 装备不存在
     */
    int EQUIPMENT_NO_EXIST = 50000;

    /**
     * 该道具不是装备类型
     */
    int ITEM_NO_EQUIPMENT = 50001;

    /**
     * 玩家职业和道具类型不匹配
     */
    int PLAYER_TYPE_NO_MATCH_EQUIPMENT = 50002;

    /**
     * 该部位不存在装备
     */
    int POSITION_NO_EXIST_EQUIPMENT = 50003;

    /**
     * 该装备部位没定义
     */
    int POSITION_NO_DEFINE = 50004;

    /**
     * 装备栏卡槽位置参数不合法
     */
    int SLOT_POSITION_ILLEGAL = 50005;

    /**
     * 装备栏该卡槽已升满级
     */
    int SLOT_LEVEL_CELL = 50006;

    /**
     * 卡槽升级所需道具不足
     */
    int SLOT_UP_ITEM_NO_ENOUGH = 50007;

}
