package com.example.gamedemo.common.utils;

import com.example.gamedemo.common.dispatcher.ControllerManager;
import com.example.gamedemo.server.common.MsgPacket;

import java.lang.reflect.Field;

/**
 * @author wengj
 * @description：编码工具
 * @date 2019/6/10
 */
public class DecoderUtils {

    /**
     * 将字符串数据转换为msgPacket对象
     *
     * @param msg
     * @return
     */
    public static MsgPacket transformMsg2MsgPacket(String msg) {

        Class classByCmd = ControllerManager.getClassByCmd(msg.split(" ")[0]);

        Object packet = null;
        MsgPacket msgPacket = new MsgPacket();

        try {
            packet = classByCmd.newInstance();

            Field[] declaredFields = classByCmd.getDeclaredFields();

            String[] msgs = msg.split(" ");
            msgPacket.setCmd(msgs[0]);
            for (int i = 0; i < declaredFields.length; i++) {
                declaredFields[i].setAccessible(true);
                Class<?> fieldType = declaredFields[i].getType();
                String value = msgs[i + 1];
                if (String.class == fieldType) {
                    declaredFields[i].set(packet, String.valueOf(value));
                } else if ((Integer.TYPE == fieldType)
                        || (Integer.class == fieldType)) {
                    declaredFields[i].set(packet, Integer.valueOf(value));
                } else if ((Long.TYPE == fieldType)
                        || (Long.class == fieldType)) {
                    declaredFields[i].set(packet, Long.valueOf(value));
                } else if ((Float.TYPE == fieldType)
                        || (Float.class == fieldType)) {
                    declaredFields[i].set(packet, Float.valueOf(value));
                } else if ((Short.TYPE == fieldType)
                        || (Short.class == fieldType)) {
                    declaredFields[i].set(packet, Short.valueOf(value));
                } else if ((Double.TYPE == fieldType)
                        || (Double.class == fieldType)) {
                    declaredFields[i].set(packet, Double.valueOf(value));
                } else if (Character.TYPE == fieldType) {
                    if (value.length() > 0) {
                        declaredFields[i].set(packet, value.charAt(0));
                    }
                }
            }
            msgPacket.setMsg(packet);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return msgPacket;
    }

}
