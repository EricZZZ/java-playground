public class MyDataShare {

    public static void main(String[] args) {
        MyData data = new MyData();
        Runnable add = new AddRunnable(data);
        Runnable dec = new DecRunnable(data);
        for (int i = 0; i < 2; i++) {
            new Thread(add).start();
            new Thread(dec).start();
        }
    }
}

class MyData {
    // 将数据抽象成 MyData 类，并将数据的操作（add、dec 方法）作为类的方法
    private int j = 0;

    public synchronized void add() {
        j++;
        System.out.println("线程" + Thread.currentThread().getName() + " 执行add，" + "j为：" + j);
    }

    public synchronized void dec() {
        j--;
        System.out.println("线程" + Thread.currentThread().getName() + " 执行dec，" + "j为：" + j);
    }

    public int getData() {
        return j;
    }

}

class AddRunnable implements Runnable {
    MyData data;

    // 线程使用该类的对象并调用类的方法对数据进行操作
    public AddRunnable(MyData data) {
        this.data = data;
    }

    @Override
    public void run() {
        data.add();
    }

}

class DecRunnable implements Runnable {
    MyData data;

    public DecRunnable(MyData data) {
        this.data = data;
    }

    @Override
    public void run() {
        data.dec();
    }

}