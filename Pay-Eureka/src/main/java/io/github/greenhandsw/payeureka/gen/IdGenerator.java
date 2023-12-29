package io.github.greenhandsw.payeureka.gen;

public class IdGenerator {
    // 起始时间
    private final long from = 1422720000000L;
    // 机器位所占bit位数
    private final long instanceIdBits = 10L;
    // 序列号所占bit位数
    private final long sequenceBits = 12L;

    // 机器位左移长度
    private final long instanceIdShift = sequenceBits;
    // 时间位左移长度
    private final long timestampLeftShift = sequenceBits + instanceIdBits;

    // 序号1: 最大机器ID
    private final long maxInstanceId = -1L ^ (-1L << instanceIdBits);
    // 最大序列号
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long instanceId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public IdGenerator(long instanceId) {
        if (instanceId > maxInstanceId || instanceId < 0) {
            throw new IllegalArgumentException(String.format("instance Id can't be greater than %d or less than 0", maxInstanceId));
        }
        this.instanceId = instanceId;
    }
    //  序号2:
    public synchronized long nextId() {
        long timestamp = timeGen();
        //  序号3:
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //  序号4:
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextSecs(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;
        //  序号5:
        return ((timestamp - from) << timestampLeftShift)  // (当前时间 - 起始时间) 向左移位
                | (instanceId << instanceIdShift)  // 机器位 向左移位
                | sequence; // 序列位
    }

    private long tilNextSecs(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
