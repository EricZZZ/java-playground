import java.util.concurrent.atomic.AtomicInteger;

class AtomicDemo implements Runnable {
    private static AtomicInteger num = new AtomicInteger(0);
    private static int num1 = 0;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        System.out.println(Thread.currentThread().getName() + " " + getAutoIncrease() + "," + getIncrease());
    }

    public int getAutoIncrease() {
        return num.getAndIncrement();
    }

    public int getIncrease() {
        return num1++;
    }
}

public class Test {
    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();
        for (int i = 0; i < 5; i++) {
            new Thread(ad).start();
        }
    }
}
