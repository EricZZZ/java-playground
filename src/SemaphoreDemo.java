import java.util.concurrent.Semaphore;

// Semaphore 与锁相似，主要用于控制资源的并发访问
public class SemaphoreDemo {

    static class Worker extends Thread {

        private int num;
        private Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();// 线程申请资源，即员工申请打印机
                System.out.println("员工" + this.num + "占用一个打印机。");
                Thread.sleep(3000);
                System.out.println("员工" + this.num + "打印完成，释放出打印机。");
                semaphore.release();// 线程释放资源，即员工在使用完成后释放打印机资源

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        int printNumber = 5;// 设置线程数，即员工数量
        Semaphore semaphore = new Semaphore(2);// 设置并发数，即打印机数量

        for (int i = 0; i < printNumber; i++) {
            new Worker(i, semaphore).start();
        }

    }
}
