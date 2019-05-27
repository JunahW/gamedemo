package com.example.gamedemo.server.game.account.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wengj
 * @description
 * @date 2019/5/27
 */
@Entity
@Table(name = "player")
public class Player {

    @Id
    private int id;

    @Column
    private String name;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
