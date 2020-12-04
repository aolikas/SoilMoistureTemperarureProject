package my.e.soilmoisturetemperarureproject.Model;

public class Data {

    public String humidityCondition;
    public String sensorDescription;
    public int humidity;
    public float temperature;

    public Data() {
    }

    public Data(String humidityCondition, String sensorDescription, int humidity, float temperature) {
        this.humidityCondition = humidityCondition;
        this.sensorDescription = sensorDescription;
        this.humidity = humidity;
        this.temperature = temperature;
    }
}
