package com.example.gamedemo.server.game.player.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;
import com.example.gamedemo.server.game.attribute.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wengj
 * @description:玩家属性
 * @date 2019/6/3
 */
@Resource
public class BaseAttributeResource implements ResourceInterface {

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

    private List<Attribute> attributes;

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

    public List<Attribute> getAttributes() {
        return attributes;
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

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Object getId() {
        return this.playerType;
    }

    /**
     * 获取基础属性列表
     *
     * @return
     */
    public List<Attribute> getPlayerBaseAttribute() {
        if (this.attributes == null) {
            ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute("hp", this.hp));
            attributes.add(new Attribute("mp", this.mp));
            attributes.add(new Attribute("attack", this.attack));
            attributes.add(new Attribute("defense", this.defense));
            attributes.add(new Attribute("attackUpper", this.attackUpper));
            attributes.add(new Attribute("defenseUpper", this.defenseUpper));
            this.attributes = attributes;
        }
        return attributes;
    }
}
