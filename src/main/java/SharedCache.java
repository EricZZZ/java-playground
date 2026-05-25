import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SharedCache {
    private final Map<String, String> cache = new HashMap<>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    // 写入数据
    public void put(String key, String value) {
        rwLock.writeLock().lock();// 加锁
        try {
            System.out.println(Thread.currentThread().getName() + " 正在写入...");
            Thread.sleep(500); // 模拟耗时操作
            cache.put(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 写入完成");
            rwLock.writeLock().unlock();// 释放锁
        }
    }

    // 读取数据
    public String get(String key) {
        rwLock.readLock().lock(); // 加上读锁
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取...");
            Thread.sleep(200); // 模拟耗时操作
            return cache.get(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            System.out.println(Thread.currentThread().getName() + " 读取完成");
            rwLock.readLock().unlock(); // 释放读锁
        }
    }

    public static void main(String[] args) {
        SharedCache myCache = new SharedCache();

        
        // 启动三个读线程
        for (int i = 1; i <= 3; i++) {
            new Thread(() -> {
                System.out.println("结果: " + myCache.get("data"));
            }, "Reader-" + i).start();
        }

        // 启动一个写线程
        new Thread(() -> myCache.put("data", "Hello World"), "Writer").start();

    }
}
