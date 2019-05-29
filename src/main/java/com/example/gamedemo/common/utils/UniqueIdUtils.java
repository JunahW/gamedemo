package com.example.gamedemo.common.utils;


/**
 * @author: wengj
 * @date: 2019/5/29
 * @description: 生成全局唯一id
 */
public class UniqueIdUtils {

    /**
     * 起始的时间戳
     * <p>
     * 某个时间点相对1970-01-01的毫秒数
     */
    private final static long START_STAMP = 1501516800000L;

    /**
     * 序列号占用的位数
     */
    private final static long SEQUENCE_BIT = 12;

    /**
     * 机器标识占用的位数
     */
    private final static long MACHINE_BIT = 5;

    /**
     * 数据中心占用的位数
     */
    private final static long DATA_CENTER_BIT = 5;

    /**
     * 每一部分的最大值
     * <p>
     * -1L^(-1L<<n)表示n个bit的数字最大值 相关知识 异或 位移 原码 反码 补码
     */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;


    /**
     * 数据中心
     */
    private final static long DATACENTER_ID = 1;
    /**
     * 机器标识
     */
    private final static long MACHINE_ID = 1;
    /**
     * 序列号
     */

    private static long sequence = 0L;
    /**
     * 上一次时间戳
     */

    private static long lastStamp = -1L;


    /**
     * 产生下一个ID
     *
     * @return
     */
    public static synchronized long nextId() {
        long currStmp = getNewStamp();
        if (currStmp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStamp = currStmp;
        // 时间戳部分
        return (currStmp - START_STAMP) << TIMESTAMP_LEFT
                | DATACENTER_ID << DATA_CENTER_LEFT // 数据中心部分
                | MACHINE_ID << MACHINE_LEFT // 机器标识部分
                | sequence; // 序列号部分
    }

    private static long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private static long getNewStamp() {
        return System.currentTimeMillis();
    }


    public static void main(String[] args) {
        long id = UniqueIdUtils.nextId();
        System.out.println(id);
        System.out.println("================");
    }
}