package com.example.gamedemo.server.game.account.constant;

/**
 * @author: wengj
 * @date: 2019/4/28
 * @description: 账户相关指令
 */
public interface AccountCmd {
    /**
     * 创建
     */
    String CREATE = "create";

    /**
     * 查询
     */
    String GET = "get";

    /**
     * 登陆
     */
    String LOGIN = "login";

    /**
     * 登出
     */
    String LOGOUT = "logout";

}
