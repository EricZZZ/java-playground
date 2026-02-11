package com.ericzzz.j8;

public interface B extends A {
    default void hello() {
        System.out.println("Hello from B");
    }
}
