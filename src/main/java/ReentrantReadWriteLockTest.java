import java.util.concurrent.locks.ReentrantReadWriteLock;

class Demo {
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private int data = 0;

    public void read() {
        rwl.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "=== 开始读取数据 ===");
            Thread.sleep(2000);
            // rwl.wait(2000); // 不能这样使用
            System.out.println(Thread.currentThread().getName() + "读取完毕，数据为:" + data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.readLock().unlock();
        }

    }

    public void write(int newValue) {
        rwl.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " [!!!] 开始写入数据 [!!!]");
            this.data = newValue;
            Thread.sleep(1000); // 模拟写入耗时
            // rwl.wait(1000); // 不能这样使用
            System.out.println(Thread.currentThread().getName() + " 写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.writeLock().unlock();
        }
    }

}

public class ReentrantReadWriteLockTest {

    public static void main(String[] args) {
        Demo demo = new Demo();
        new Thread(() -> demo.read(), "读线程A").start();

        new Thread(() -> demo.read(), "读线程B").start();

        new Thread(() -> demo.write(8), "写线程W").start();
    }

}
