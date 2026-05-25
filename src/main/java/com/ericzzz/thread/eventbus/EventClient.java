package com.ericzzz.thread.eventbus;

import java.util.concurrent.TimeUnit;

import com.ericzzz.thread.eventbus.EventQueue.Event;

public class EventClient {
    public static void main(String[] args) {
        final EventQueue eventQueue = new EventQueue();
        new Thread(() -> {
            for (;;) {
                eventQueue.offer(new Event());
            }
        }, "EventProducer").start();

        new Thread(() -> {
            for (;;) {
                eventQueue.take();
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "EventConsumer").start();
    }

}
