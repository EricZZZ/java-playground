package com.ericzzz.thread.observable;

import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        final TaskLifecycle<String> lifecycle = new TaskLifecycle.EmptyLifecycle<String>() {
            public void onFinish(Thread thread, String result) {
                System.out.println("The result is " + result);
            }
        };

        Observable observableThread = new ObservableThread<>(lifecycle, () -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                return "Hello World";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The task is done.");
            return "Hello Observer";
        });

        observableThread.start();

    }
}
