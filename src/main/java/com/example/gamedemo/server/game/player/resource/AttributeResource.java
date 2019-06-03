package com.example.gamedemo.server.game.player.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;

/**
 * @author wengj
 * @description:玩家属性
 * @date 2019/6/3
 */
@Resource
public class AttributeResource implements ResourceInterface {

    /**
     * 玩家类型（职业）
     */
    @ExcelColumn(columnName = "playerType")
    private String playerType;

    /**
     * 生命值
     */
    @ExcelColumn(columnName = "hp")
    private double hp;

    /**
     * 魔法值
     */
    @ExcelColumn(columnName = "mp")
    private double mp;

    /**
     * 攻击力
     */
    @ExcelColumn(columnName = "attack")
    private double attack;

    /**
     * 防御力
     */
    @ExcelColumn(columnName = "defense")
    private double defense;

    /**
     * 伤害加成
     */
    @ExcelColumn(columnName = "attackUpper")
    private double attackUpper;

    /**
     * 受到伤害减少
     */
    @ExcelColumn(columnName = "defenseUpper")
    private double defenseUpper;

    public String getPlayerType() {
        return playerType;
    }

    public double getHp() {
        return hp;
    }

    public double getMp() {
        return mp;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefense() {
        return defense;
    }

    public double getAttackUpper() {
        return attackUpper;
    }

    public double getDefenseUpper() {
        return defenseUpper;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public void setMp(double mp) {
        this.mp = mp;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public void setAttackUpper(double attackUpper) {
        this.attackUpper = attackUpper;
    }

    public void setDefenseUpper(double defenseUpper) {
        this.defenseUpper = defenseUpper;
    }

    @Override
    public Object getId() {
        return this.playerType;
    }
}
