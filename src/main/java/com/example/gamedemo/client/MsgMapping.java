package com.example.gamedemo.client;


import com.example.gamedemo.server.game.account.packet.CM_LoginAccount;

import java.util.HashMap;

/**
 * @author wengj
 * @description
 * @date 2019/6/10
 */
public class MsgMapping {

    private static HashMap<String, Class> cmdClassMap = new HashMap<>();

    static {
        cmdClassMap.put("loginAccount", CM_LoginAccount.class);
    }

    /**
     * 通过指令获取class
     *
     * @param cmd
     * @return
     */
    public static Class getClassByCmd(String cmd) {
        return cmdClassMap.get(cmd);
    }
}
