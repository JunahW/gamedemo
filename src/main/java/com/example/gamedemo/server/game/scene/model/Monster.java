package com.example.gamedemo.server.game.scene.model;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;

/**
 * @author wengj
 * @description：怪物
 * @date 2019/5/21
 */
@Resource
public class Monster extends SceneObject {

    /**
     * 血量
     */
    @ExcelColumn(columnName = "hp")
    private int hp;

    /**
     * 魔法值
     */
    @ExcelColumn(columnName = "mp")
    private int mp;

    /**
     * 攻击力
     */
    @ExcelColumn(columnName = "attack")
    private int attack;


}
