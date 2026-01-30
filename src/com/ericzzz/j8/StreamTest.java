package com.ericzzz.j8;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class StreamTest {

    public static void main(String[] args) {

        List<Dish> menu = Arrays.asList(
                new Dish("pork", 800, false, Dish.Type.MEAT),
                new Dish("beef", 700, false, Dish.Type.MEAT),
                new Dish("chicken", 400, false, Dish.Type.MEAT),
                new Dish("french fries", 530, true, Dish.Type.OTHER),
                new Dish("rice", 350, true, Dish.Type.OTHER),
                new Dish("season fruit", 120, true, Dish.Type.OTHER),
                new Dish("pizza", 550, false, Dish.Type.OTHER),
                new Dish("prawns", 300, true, Dish.Type.FISH),
                new Dish("salmon", 450, false, Dish.Type.MEAT));

        // 筛选出卡路里大于300的菜肴的名称，只保留前3个
        List<String> threeHighCaloricDishNames = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(toList());
        System.out.println(threeHighCaloricDishNames);

        // 计算菜单中所有菜肴的总卡路里
        int totalCalories = menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
        System.out.println("StreamTest.main() totalCalories = " + totalCalories);
        totalCalories = menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println("StreamTest.main() totalCalories = " + totalCalories);

        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
        System.out.println("StreamTest.main() dishesByType = " + dishesByType);

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        int max = list.stream().reduce(0, Integer::max);
        System.out.println("StreamTest.main() max = " + max);

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950));
        // 找出2011年发生的所有交易，并按交易额排序(从低到高)。
        List<Transaction> result_1 = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(toList());
        System.out.println(result_1);

        // 交易员都在哪些不同的城市工作过？
        List<String> result_2 = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(toList());
        System.out.println(result_2);

        // 查找所有来自剑桥的交易员，并按姓名排序。
        List<Trader> result_3 = transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader)
                .sorted(Comparator.comparing(Trader::getName))
                .collect(toList());
        System.out.println(result_3);

        // 返回所有交易员的姓名字符串，按字母顺序排序。
        String result_4 = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .collect(joining());
        System.out.println(result_4);

        // 有没有交易员是在米兰工作的？
        Boolean result_5 = transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println(result_5);

        // 打印所有交易员在剑桥工作的交易的总交易额。
        transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(t -> t.getValue())
                .forEach(System.out::println);

        // 所有交易中，最高的交易额是多少？
        int result_7 = transactions.stream()
                .map(t -> t.getValue())
                .reduce(0, Integer::max);
        System.out.println(result_7);

        // 找到交易额最小的交易。
        Transaction result_8 = transactions.stream()
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2)
                .get();
        System.out.println(result_8);
    }
}

class Trader {
    private final String name;
    private final String city;

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;

    }

    public String toString() {
        return "Trader:" + this.name + " in " + this.city;
    }
}

class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "{" + this.trader + ", " +
                "year: " + this.year + ", " +
                "value:" + this.value + "}";
    }
}

class Dish {
    private final String name;
    private final int calories;
    private final boolean vegetarian;
    private final Type type;

    public enum Type {
        MEAT, FISH, OTHER
    }

    public Dish(String name, int calories, boolean vegetarian, Type type) {
        this.name = name;
        this.calories = calories;
        this.vegetarian = vegetarian;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }
}
