package com.ericzzz.concurrent;

/**
 * 《Java并发编程实战》书中 可见性问题，可能会循环下去，或者可能会打印0
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws Exception {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
