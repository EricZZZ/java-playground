import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) {
        // 如果cout 没有coutDown，.await 会一直等待
        CountDownLatch latch = new CountDownLatch(2);
        long start = System.currentTimeMillis();

        new Thread(() -> {
            System.out.println("第一个线程开始工作");
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第一个线程工作结束");
            latch.countDown();
        }).start();

        new Thread(() -> {
            System.out.println("第二个线程开始工作");
            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第二个线程工作结束");
            latch.countDown();
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有任务都已经完成");
        long end = System.currentTimeMillis();
        System.out.println("总共用时：" + (end - start));
    }
}
