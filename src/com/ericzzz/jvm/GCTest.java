package com.ericzzz.jvm;

public class GCTest {
    private static final int _1MB = 1024 * 1024;

    /**
     * 对象优先分配在新生代Eden区
     * VM参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseSerialGC
     * -Xlog:'gc*'
     * -XX:SurvivorRatio=8
     * 
     * -Xms20M：堆的初始大小为20MB
     * -Xmx20M：堆的最大大小为20MB
     * -Xmn10M：新生代的大小为10MB
     * -XX:+UseSerialGC：使用Serial垃圾收集器
     * -Xlog:'gc*'：打印GC详细信息
     * -XX:SurvivorRatio=8：新生代中Eden区和Survivor区的比例为8:1
     */
    @SuppressWarnings("unused")
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB]; // 出现一次Minor GC
    }

    /**
     * 大对象直接进入老年代
     * VM参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseSerialGC
     * -Xlog:'gc*'
     * -XX:SurvivorRatio=8
     * -XX:PretenureSizeThreshold=3145728：大对象直接进入老年代的阈值为3MB
     */
    @SuppressWarnings("unused")
    public static void testPretenureSizeThreshold(){
        byte[] allocation = new byte[4 * _1MB]; // 直接分配在老年代
    }

    /**
     * 长期存活的对象进入老年代
     * VM参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseSerialGC
     * -Xlog:'gc*'
     * -XX:SurvivorRatio=8
     * -XX:MaxTenuringThreshold=1：对象在Survivor区中经历1次Minor GC后进入老年代
     * -XX:+PrintGCDetails：打印GC详细信息
     */
    @SuppressWarnings("unused")
    public static void testTenuringThreshold(){
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4]; // 什么时候进入老年代取决于XX:MaxTenuringThreshold参数
        allocation2 = new byte[4 * _1MB]; 
        allocation3 = new byte[4 * _1MB]; 
        allocation3 = null;
        allocation3 = new byte[4 * _1MB]; // 再次分配，会触发一次Minor GC
    }

    public static void main(String[] args) {
        // 测试对象优先分配在新生代Eden区
        // testAllocation();
        // 测试大对象直接进入老年代
        testPretenureSizeThreshold();
    }
}
