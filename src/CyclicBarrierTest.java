import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(2);

        new Thread(() -> {
            System.out.println("第一个线程开始工作");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("第一个线程等待其他线程完成工作");
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("第一个线程继续工作");
        }).start();
        new Thread(() -> {
            System.out.println("第二个线程开始工作");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("第二个线程等待其他线程完成工作");
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("第二个线程继续工作");
        }).start();
    }
}
