package com.ericzzz.concurrent.juc.utils;

import static java.util.concurrent.ThreadLocalRandom.current;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierExample2 {

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        // 专门用一个计数器来让导游知道现在是该上车还是该下车
        final AtomicInteger phase = new AtomicInteger(1);
        // 只有 10 个游客参与计数。传入 Runnable，当 10 个线程都到齐时自动触发
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
            if (phase.get() == 1) {
                System.out.println(">>> Tour Guider: All tourists have got ON the bus. Driving... <<<");
                phase.incrementAndGet();
            } else {
                System.out.println(">>> Tour Guider: All tourists have got OFF the bus. Trip finished! <<<");
            }
        });
        // 启动 10 个游客线程
        for (int i = 0; i < 10; i++) {
            new Thread(new Tourist(i, cyclicBarrier)).start();
        }
    }

    private static class Tourist implements Runnable {
        private final int touristID;
        private final CyclicBarrier barrier;

        public Tourist(int touristID, CyclicBarrier barrier) {
            this.touristID = touristID;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            // 第一阶段：上车
            this.spendSeveralSeconds();
            this.waitAndPrint("Tourist:%d Get on the bus, and waiting for others...\n");

            // 第二阶段：坐车前往目的地
            System.out.printf("Tourist:%d is enjoying the scenery on the bus...\n", touristID);
            this.spendSeveralSeconds();

            // 第三阶段：下车
            this.waitAndPrint("Tourist:%d Get off the bus, and waiting for others...\n");
        }

        private void waitAndPrint(String message) {
            System.out.printf(message, touristID);
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void spendSeveralSeconds() {
            try {
                TimeUnit.SECONDS.sleep(current().nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
