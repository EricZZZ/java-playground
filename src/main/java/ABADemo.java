import java.util.concurrent.atomic.AtomicInteger;

public class ABADemo {

    private static AtomicInteger atomicInteger = new AtomicInteger(100);

    public static void main(String[] args) {
        // 线程 1：模拟捣乱的线程 (把 A 变成 B，再变回 A)
        new Thread(() -> {
            atomicInteger.compareAndSet(100, 110);
            System.out.println(Thread.currentThread().getName() + " 修改: 100 -> 110");

            atomicInteger.compareAndSet(110, 100);
            System.out.println(Thread.currentThread().getName() + " 修改: 110 -> 100 (完成 ABA)");
        }, "Thread-Troublemaker").start();

        // 线程 2：预期的修改线程 (想把 100 变成 101)
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // CAS 检查，发现值还是 100，修改成功！
            // 但它并不知道这个 100 已经不是最初那个 100 了
            boolean success = atomicInteger.compareAndSet(100, 101);
            System.out.println(Thread.currentThread().getName() + " 修改成功了吗? " + success + "，当前值: " + atomicInteger.get());
        }, "Thread-Main").start();
        ;

    }
}
