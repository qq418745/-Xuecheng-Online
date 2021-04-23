package main.netty.no;



/**
 * 显示当前天气情况（可以理解成是气象站自己的网站）
 */

public class CurrentConditions implements Observer{

    // 温度，气压，湿度
    private float temperature;
    private float pressure;
    private float humidity;



    //更新 天气情况，是由 WeatherData 来调用的，即：此处使用的是推送模式
    public void update(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        display();
    }


    public void display() {
        System.out.println("***Today mTemperature: " + temperature + "***");
        System.out.println("***Today mPressure: " + pressure + "***");
        System.out.println("***Today mHumidity: " + humidity + "***");
    }

}