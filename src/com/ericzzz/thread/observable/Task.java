package com.ericzzz.thread.observable;

@FunctionalInterface
public interface Task<T> {
    T call();
}
