package my.e.soilmoisturetemperarureproject.Model;

public class SensorsData {

    private String humidityCondition;
    private float temperature;

    public SensorsData() {
    }

    public SensorsData(String humidityCondition, float temperature) {
        this.humidityCondition = humidityCondition;
        this.temperature = temperature;
    }

    public String getHumidityCondition() {
        return humidityCondition;
    }

    public void setHumidityCondition(String humidityCondition) {
        this.humidityCondition = humidityCondition;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
