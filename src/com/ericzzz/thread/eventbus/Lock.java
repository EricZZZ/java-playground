package com.ericzzz.thread.eventbus;

import java.util.List;
import java.util.concurrent.TimeoutException;

public interface Lock {
    void lock() throws InterruptedException;

    void lock(long millis) throws InterruptedException, TimeoutException;

    void unlock();

    List<Thread> getBlockedThreads();
}
