package com.ericzzz.jvm;

/*
 * 实际上jvm用的是可达性分析算法
 */
public class ReferenceCountingGC {
    public Object instance = null;

    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    // 添加finalize方法观察对象回收
    @Override
    protected void finalize() throws Throwable {
        System.out.println(this + " is being garbage collected!");
        super.finalize();
    }

    public static void testGC() {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        System.gc();

        // 给GC一些时间
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testGC();
    }
}
