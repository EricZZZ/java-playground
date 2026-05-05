package com.ericzzz.thread.eventbus;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

import java.util.concurrent.TimeUnit;

public class BooleanLockTest {
    private final BooleanLock lock = new BooleanLock();

    public void syncMethod() {
        try {
            lock.lock();
            int randomInt = current().nextInt(10);
            System.out.println(currentThread() + " get the lock.");
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 测试 多个线程通过lock()方法争夺锁
        BooleanLockTest blt = new BooleanLockTest();
        // IntStream.range(0, 10).mapToObj(i -> new
        // Thread(blt::syncMethod)).forEach(Thread::start);

        // 测试可中断被阻塞的线程
        new Thread(blt::syncMethod, "T1").start();
        TimeUnit.MICROSECONDS.sleep(2);
        Thread t2 = new Thread(blt::syncMethod, "T2");
        t2.start();
        TimeUnit.MICROSECONDS.sleep(10);
        t2.interrupt();
    }
}
