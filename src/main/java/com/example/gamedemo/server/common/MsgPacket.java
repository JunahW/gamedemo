package com.example.gamedemo.server.common;

/**
 * @author wengj
 * @description：消息包装（相当于协议）
 * @date 2019/6/10
 */
public class MsgPacket {
  /** 指令 */
  private String cmd;

  /** 消息对象 */
  private Object msg;

  public String getCmd() {
    return cmd;
  }

  public void setCmd(String cmd) {
    this.cmd = cmd;
  }

  public Object getMsg() {
    return msg;
  }

  public void setMsg(Object msg) {
    this.msg = msg;
  }

  @Override
  public String toString() {
    return "MsgPacket{" + ", cmd='" + cmd + '\'' + ", msg=" + msg + '}';
  }
}
