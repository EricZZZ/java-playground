import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABASolution {

    private static AtomicStampedReference<Integer> stampedRef = new AtomicStampedReference<Integer>(100, 500);

    public static void main(String[] args) throws InterruptedException {

        // 线程 2：预期的修改线程 (想把 100 变成 101)
        new Thread(() -> {
            int stamp = stampedRef.getStamp();
            try {
                TimeUnit.NANOSECONDS.sleep(1);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // CAS 检查，发现值还是 100，修改成功！
            // 但它并不知道这个 100 已经不是最初那个 100 了
            boolean success = stampedRef.compareAndSet(100, 101, stamp, stamp + 1);
            System.out.println("修改成功了吗? " + success + "，当前最新版本: " + stampedRef.getStamp());
        }, "Thread-Main").start();

        // 线程 1：模拟捣乱的线程 (把 A 变成 B，再变回 A)
        new Thread(() -> {
            int stamp = stampedRef.getStamp(); // 获取初始版本号
            System.out.println(Thread.currentThread().getName() + " 初始版本: " + stamp);

            stampedRef.compareAndSet(100, 110, stampedRef.getStamp(), stampedRef.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + " 修改: 100 -> 110");

            stampedRef.compareAndSet(110, 100, stampedRef.getStamp(), stampedRef.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + " 修改: 110 -> 100 (完成 ABA)");

        }, "Thread-Troublemaker").start();

    }
}
