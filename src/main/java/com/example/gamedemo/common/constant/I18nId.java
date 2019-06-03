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

    /**==========================装备相关=========================**/
    /**
     * 装备不存在
     */
    int EQUIPMENT_NO_EXIST = 20000;

    /**
     * 该道具不是装备类型
     */
    int ITEM_NO_EQUIPMENT = 20001;

    /**
     * 玩家职业和道具类型不匹配
     */
    int PLAYER_TYPE_NO_MATCH_EQUIPMENT = 20002;

    /**
     * 该部位不存在装备
     */
    int POSITION_NO_EXIST_EQUIPMENT = 20003;

    /**
     * 该装备部位没定义
     */
    int POSITION_NO_DEFINE = 20004;

}
