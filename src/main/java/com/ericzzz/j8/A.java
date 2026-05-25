package com.ericzzz.j8;

public interface A {
    default void hello() {
        System.out.println("Hello from A");
    }
}
