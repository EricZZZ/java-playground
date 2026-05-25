package com.ericzzz.thread.eventbus;

import static java.lang.Thread.currentThread;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class BooleanLock implements Lock {
    private Thread currentThread;

    private boolean isLocked = false;

    private final List<Thread> blockedThreads = new LinkedList<>();

    @Override
    public List<Thread> getBlockedThreads() {
        return Collections.unmodifiableList(blockedThreads);
    }

    @Override
    public void lock() throws InterruptedException {
        synchronized (this) {
            while (isLocked) {
                blockedThreads.add(currentThread());
                this.wait();
            }
            blockedThreads.remove(currentThread());
            this.isLocked = true;
            this.currentThread = currentThread();
        }

    }

    @Override
    public void lock(long millis) throws InterruptedException, TimeoutException {
        synchronized (this) {
            if (millis <= 0) {
                this.lock();
            } else {
                long remainingMills = millis;
                long endMills = System.currentTimeMillis() + remainingMills;
                while (isLocked) {
                    if (remainingMills <= 0)
                        throw new TimeoutException("can not get the lock during " + millis);
                    if (!blockedThreads.contains(currentThread())) {
                        blockedThreads.add(currentThread());
                        this.wait(remainingMills);
                        remainingMills = endMills - System.currentTimeMillis();
                    }
                    blockedThreads.remove(currentThread());
                    this.isLocked = true;
                    this.currentThread = currentThread();
                }
            }
        }

    }

    @Override
    public void unlock() {
        synchronized (this) {
            if (currentThread == currentThread()) {
                this.isLocked = false;
                Optional.of(currentThread().getName() + " release the lock.")
                        .ifPresent(System.out::println);
                this.notifyAll();
            }
        }

    }

}
