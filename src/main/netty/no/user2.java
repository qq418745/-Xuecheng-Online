package main.netty.no;

public class user2 implements Observer{
    @Override
    public void update(float temperature, float pressure, float humidity) {
        System.out.println("***Today mTemperature: " + temperature + "***");
        System.out.println("***Today mPressure: " + pressure + "***");
        System.out.println("***Today mHumidity: " + humidity + "***");
    }
}
