import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TouristGroup {
    public static void main(String[] args) {
        // 1. 创建屏障，要求 3 个线程到齐。
        // 第二个参数是 Runnable：当所有人到齐时，由最后一个到达的线程执行该任务。
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println("\n===== 满 3 人了，大巴车出发！ =====\n");
        });
        // 模拟 6 个游客（分两波出发）
        for (int i = 1; i <= 6; i++) {
            final int touristId = i;
            new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 3000));
                    System.out.println("游客 " + touristId + " 到达集合点");
                    // 2. 关键点：线程在这里停下，等待其他队友
                    barrier.await();
                    // 3. 屏障开启后，大家继续往后走
                    System.out.println("游客 " + touristId + " 开始登车...");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
