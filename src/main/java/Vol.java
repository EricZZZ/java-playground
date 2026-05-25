public class Vol {
    static volatile int intVal = 0;

    public static void main(String[] args) {
        // 创建 10 个线程，执行简单的自加操作
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // synchronized (Vol.class) {
                for (int j = 0; j < 1000; j++)
                    intVal++;
                // }
            }).start();
        }
        // 保证之前启动的全部线程执行完毕
        while (Thread.activeCount() > 1)
            Thread.yield();
        System.out.println(intVal);
    }
}
