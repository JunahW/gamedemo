package com.example.gamedemo.server;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.dispatcher.ControllerManager;
import com.example.gamedemo.common.dispatcher.InvokeMethod;
import com.example.gamedemo.common.resource.ResourceInterface;
import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.common.utils.ApplicationContextProvider;
import com.example.gamedemo.common.utils.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wengj
 * @description 系统初始化
 * @date 2019/5/7
 */
@Component
public class SystemInitializer {

    private static Logger logger = LoggerFactory.getLogger(SystemInitializer.class);


    /**
     * 初始化指令和处理方法映射表
     */
    public static void initControllerMap() {
        logger.info("开始初始化指令和处理方案映射表");
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(HandlerClass.class);
        Set<Map.Entry<String, Object>> entries = beansWithAnnotation.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Class<?> aClass = entry.getValue().getClass();
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(HandlerMethod.class)) {
                    HandlerMethod annotation = method.getAnnotation(HandlerMethod.class);
                    String cmd = annotation.cmd();
                    InvokeMethod invokeMethod = new InvokeMethod(entry.getValue(), method);
                    ControllerManager.add(cmd, invokeMethod);
                    //初始化指令和消息映射关系
                    Parameter[] parameters = method.getParameters();
                    ControllerManager.addPacket(cmd, parameters[1].getType());
                }
            }
        }
        logger.info("完成初始化指令和处理方案映射表");
    }

    /**
     * 初始化静态资源
     */
    public static void initResource() {
        logger.info("开始初始化静态资源");
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Resource.class);
        Set<Map.Entry<String, Object>> entries = beansWithAnnotation.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Class<?> aClass = entry.getValue().getClass();
            List<?> list = ExcelUtils.importExcel((entry.getValue().getClass()));
            for (Object object : list) {
                ResourceInterface resourceItem = (ResourceInterface) object;
                ResourceManager.putResourceItem(aClass, resourceItem.getId(), object);
            }
        }
        logger.info("完成初始化静态资源");


    }
}
