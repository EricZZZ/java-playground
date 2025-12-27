public class MyDataShare2 {

    public static void main(String[] args) {
        final MyData data = new MyData();

        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    data.add();
                }

            }).start();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    data.dec();
                }

            }).start();
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
