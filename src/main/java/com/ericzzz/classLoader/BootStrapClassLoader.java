package com.ericzzz.classLoader;

public class BootStrapClassLoader {

    public static void main(String[] args) {
        System.out.println("BootStrap:" + String.class.getClassLoader());
        // JDK 8 + 以上版本会失效
        System.out.println(System.getProperty("sun.boot.class.path"));
    }
}
