class T1 implements Runnable {

    @Override
    public void run() {
        try {
            synchronized (DeadLockDemo.obj1) {
                System.out.println("Thread t1 lock obj1");
                Thread.sleep(5000);
                synchronized (DeadLockDemo.obj2) {
                    System.out.println("Thread t1 lock obj2");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class T2 implements Runnable {

    @Override
    public void run() {

        try {
            synchronized (DeadLockDemo.obj2) {
                System.out.println("Thread t2 lock obj2");
                Thread.sleep(2000);
                synchronized (DeadLockDemo.obj1) {
                    System.out.println("Thread t2 lock obj1");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

public class DeadLockDemo {

    public static Object obj1 = new Object();
    public static Object obj2 = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(new T1());
        Thread t2 = new Thread(new T2());
        t1.start();
        t2.start();
    }

}
