package com.ericzzz.jvm;

/*
 * 任何一个对象的finalize()方法都只会被系统自动调用一次
 * 如果对象面临下一次回收，它的finalize()方法被再次执行，使用try-finally或其他方式都可以做的更好，更及时
*/
public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("I am alive!");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws Throwable {
        SAVE_HOOK = new FinalizeEscapeGC();

        // 第一次拯救自己
        SAVE_HOOK = null;
        System.gc();
        // 给GC一些时间
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("I am dead!");
        }

        // 第二次拯救自己
        SAVE_HOOK = null;
        System.gc();
        // 给GC一些时间
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("I am dead!");
        }
    }
}
