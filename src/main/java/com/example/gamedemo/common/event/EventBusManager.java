package com.example.gamedemo.common.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author wengj
 * @description：事件总线
 * @date 2019/6/11
 */
public class EventBusManager {
    /**
     * 存放事件处理映射
     */
    private static Map<Class<? extends Event>, List<ReceiverInvoke>> eventReceiverMap = new HashMap<>();

    /**
     * 提交事件并触发相应的方法
     *
     * @param event
     */
    public static void submitEvent(Event event) {
        List<ReceiverInvoke> receiverInvokeList = eventReceiverMap.get(event.getClass());
        for (ReceiverInvoke receiverInvoke : receiverInvokeList) {
            receiverInvoke.invoke(event);
        }
    }

    /**
     * 注册执行方法
     *
     * @param clazz
     * @param receiverInvoke
     */
    public static void registerReceiverInvoke(Class<? extends Event> clazz, ReceiverInvoke receiverInvoke) {
        if (eventReceiverMap.containsKey(clazz)) {
            List<ReceiverInvoke> receiverInvokes = eventReceiverMap.get(clazz);
            if (!receiverInvokes.contains(receiverInvoke)) {
                receiverInvokes.add(receiverInvoke);
            }
        } else {
            LinkedList<ReceiverInvoke> receiverInvokes = new LinkedList<>();
            receiverInvokes.add(receiverInvoke);
            eventReceiverMap.put(clazz, receiverInvokes);
        }
    }
}
