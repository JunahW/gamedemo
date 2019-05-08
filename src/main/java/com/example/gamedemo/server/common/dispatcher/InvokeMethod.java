package com.example.gamedemo.server.common.dispatcher;

import com.example.gamedemo.server.common.session.TSession;
import org.springframework.util.ReflectionUtils;

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
        return ReflectionUtils.invokeMethod(method, object, session, msg);
    }
}
