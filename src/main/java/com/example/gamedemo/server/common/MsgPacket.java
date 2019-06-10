package com.example.gamedemo.server.common;

/**
 * @author wengj
 * @description：消息包装（相当于协议）
 * @date 2019/6/10
 */
public class MsgPacket {
    /**
     * 消息指令和消息体的度
     */
    private int packetLength;
    /**
     * 指令
     */
    private String cmd;

    /**
     * 消息对象
     */
    private Object msg;

    public int getPacketLength() {
        return packetLength;
    }

    public String getCmd() {
        return cmd;
    }

    public Object getMsg() {
        return msg;
    }

    public void setPacketLength(int packetLength) {
        this.packetLength = packetLength;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MsgPacket{" +
                "packetLength=" + packetLength +
                ", cmd='" + cmd + '\'' +
                ", msg=" + msg +
                '}';
    }
}
