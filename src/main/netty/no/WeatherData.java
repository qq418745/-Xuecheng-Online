package main.netty.no;

import java.util.ArrayList;

public class WeatherData implements Subject{

    // 温度，气压，湿度
    private float temperature;
    private float pressure;
    private float humidity;

    private ArrayList<Observer> observers = new ArrayList<Observer>();





    //当数据有更新时，就调用 setData
    public void setData(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        //调用dataChange， 将最新的信息 推送给 接入方 currentConditions
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(o->o.update(this.temperature,this.pressure, this.humidity));
    }
}
