package com.example.gamedemo.server.game.account.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.packet.SM_ErrorCode;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.packet.CM_CreateAccount;
import com.example.gamedemo.server.game.account.packet.CM_LoginAccount;
import com.example.gamedemo.server.game.account.packet.CM_LogoutAccount;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/4/29
 */
@HandlerClass
@Component
public class AccountController {

  /**
   * 创建用户
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "createAccount")
  public void createAccount(TSession session, CM_CreateAccount req) {
    Account account = new Account();
    account.setAccountId(req.getAccountId());
    account.setAccountName(req.getAccountName());
    boolean flag = false;
    try {
      SpringContext.getAccountService().createAccount(account);
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("账户创建成功"));
  }

  /**
   * 登陆账户
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "loginAccount")
  public void loginAccount(TSession session, CM_LoginAccount req) {
    Account account = null;
    try {
      account = SpringContext.getAccountService().loginAccount(req.getAccountId());
      SessionManager.registerAccount(session, account);
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (account != null) {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("登陆成功"));
    }
  }

  /**
   * 退出登录
   *
   * @param session
   * @param req
   * @return
   */
  @HandlerMethod(cmd = "logout")
  public void logout(TSession session, CM_LogoutAccount req) {
    // 异步保存用户信息
    SpringContext.getAccountService().logoutAccount(session.getPlayer());
    SessionManager.close(session.getChannel());
  }
}
