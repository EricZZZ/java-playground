import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

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

class Apple {
    private String color;
    private int weight;

    public Apple(String color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Apple [color=" + color + ", weight=" + weight + "]";
    }

}

public class Test {
    public static void main(String[] args) {
        // AtomicDemo ad = new AtomicDemo();
        // for (int i = 0; i < 5; i++) {
        // new Thread(ad).start();
        // }

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(4);
        list.add(9);
        list.add(2);
        list.add(6);
        list.add(3);
        Collections.sort(list);
        System.out.println(list);

        Supplier<String> hello = () -> "hello";
        System.out.println(hello.get());

        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple("red", 600));
        apples.add(new Apple("green", 400));
        apples.add(new Apple("yellow", 400));
        apples.add(new Apple("red", 900));

        // 按重量排序
        apples.sort(comparing(Apple::getWeight));
        // 按重量排序（降序）
        apples.sort(comparing(Apple::getWeight).reversed());
        // 按重量排序（降序），如果重量相同，按颜色排序（升序）
        apples.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
        System.out.println(apples);
    }
}
