package my.e.soilmoisturetemperarureproject.Model;

public class Data {

    private String humidityCondition;
    private String sensorDescription;
    private float temperature;

    public Data() {
    }

    public Data(String sensorDescription) {
        this.sensorDescription = sensorDescription;
    }

    public Data(String humidityCondition, String sensorDescription, float temperature) {
        this.humidityCondition = humidityCondition;
        this.sensorDescription = sensorDescription;
        this.temperature = temperature;
    }

    public String getHumidityCondition() {
        return humidityCondition;
    }

    public String getSensorDescription() {
        return sensorDescription;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setHumidityCondition(String humidityCondition) {
        this.humidityCondition = humidityCondition;
    }

    public void setSensorDescription(String sensorDescription) {
        this.sensorDescription = sensorDescription;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
