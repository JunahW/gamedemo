package com.example.gamedemo.server.common.dispatcher;

import com.example.gamedemo.server.common.session.TSession;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author wengj
 * @description 请求对应的方法
 * @date 2019/5/7
 */
public class InvokeMethod {
    /**
     * 执行对象
     */
    private Object object;
    /**
     * 执行方法
     */
    private Method method;

    public Object getObject() {
        return object;
    }

    public InvokeMethod() {
    }

    public InvokeMethod(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    /**
     * 执行请求
     *
     * @param session
     * @param msg
     * @return
     */
    public Object invoke(TSession session, String msg) {
        Object req = transformMsg2Packet(msg);

        return ReflectionUtils.invokeMethod(method, object, session, req);
    }

    /**
     * 将字符串数据转换为packet对象
     *
     * @param msg
     * @return
     */
    private Object transformMsg2Packet(String msg) {

        Class classByCmd = ControllerManager.getClassByCmd(msg.split(" ")[0]);

        /**
         * 兼容先前版本
         */
        if (classByCmd == null || classByCmd == String.class) {
            return msg;
        }

        Object packet = null;
        try {
            packet = classByCmd.newInstance();

            Field[] declaredFields = classByCmd.getDeclaredFields();

            String[] msgs = msg.split(" ");
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
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return packet;
    }
}
