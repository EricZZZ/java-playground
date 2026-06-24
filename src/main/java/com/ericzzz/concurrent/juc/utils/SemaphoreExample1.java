package com.ericzzz.concurrent.juc.utils;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoreExample1 {
    public static void main(String[] args) {
        final int MAX_PERMIT_LOGIN_ACCOUNT = 10;
        final LoginService loginService = new LoginService(MAX_PERMIT_LOGIN_ACCOUNT);
        IntStream.range(0, 20).forEach(i -> new Thread(() -> {
            boolean login = loginService.login();
            if (!login) {
                System.out.println(currentThread() + " is refused due to exceed max online account.");
                return;
            }
            try {
                // 简单模拟登录成功后的系统操作
                simulateWork();
            } finally {
                // 退出系统，实际上是对许可证资源的释放
                loginService.logout();
            }
        }, "User-" + i).start());
    }

    // 随机休眠
    private static void simulateWork() {
        try {
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        } catch (InterruptedException e) {
            // ignore
        }
    }

    private static class LoginService {
        private final Semaphore semaphore;

        public LoginService(int maxPermitLoginAccount) {
            // 初始化Semaphore
            this.semaphore = new Semaphore(maxPermitLoginAccount,
                    true);
        }

        public boolean login() {
            // 获取许可证，如果获取失败该方法会返回false，tryAcquire不是一个阻塞方法
            // boolean login = semaphore.tryAcquire();
            // if (login)
            // System.out.println(currentThread() + " login success.");
            // return login;
            // // acquire为阻塞方法，会一直等待有可用的许可证并且获取之后才会返回
            try {
                semaphore.acquire();
                System.out.println(currentThread() + " login success.");
            } catch (InterruptedException e) {
                return false;
            }
            return true;

        }

        // 释放许可证
        public void logout() {
            semaphore.release();
            System.out.println(currentThread() + " logout success.");
        }
    }
}
