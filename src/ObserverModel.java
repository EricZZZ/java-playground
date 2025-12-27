import java.util.ArrayList;
import java.util.List;

interface Observer {
    void update(float temperature, float humidity);

}

interface Subject {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();
}

class WeatherData implements Subject {

    private List<Observer> observers = new ArrayList<>();
    private float temperature;
    private float humidity;

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, humidity);
        }
    }

    // 当数据更新时调用此方法
    public void setMeasurements(float temperature, float humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
        notifyObservers(); // 触发通知
    }

}

class PhoneDisplay implements Observer {

    private String name;

    public PhoneDisplay(String name) {
        this.name = name;
    }

    @Override
    public void update(float temperature, float humidity) {
        System.out.println(name + " 收到更新：当前温度为 " + temperature + "℃，湿度为 " + humidity + "%");
    }

}

public class ObserverModel {

    public static void main(String[] args) {
        // 1. 创建主题（气象站）
        WeatherData weatherStation = new WeatherData();

        // 2. 创建观察者（不同用户的手机）
        PhoneDisplay userA = new PhoneDisplay("用户A的手机");
        PhoneDisplay userB = new PhoneDisplay("用户B的手机");

        // 3. 注册订阅
        weatherStation.registerObserver(userA);
        weatherStation.registerObserver(userB);

        // 4. 模拟天气变化
        System.out.println("--- 第一次天气更新 ---");
        weatherStation.setMeasurements(25.5f, 60.0f);

        // 5. 用户B取消订阅
        weatherStation.removeObserver(userB);

        System.out.println("\n--- 第二次天气更新 ---");
        weatherStation.setMeasurements(28.0f, 55.0f);
    }

}