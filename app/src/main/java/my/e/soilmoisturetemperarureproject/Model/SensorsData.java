package my.e.soilmoisturetemperarureproject.Model;

public class SensorsData {

    private String sensorName;
    private String humidityCondition;
    private String date;
    private String description;
    private int humidity;

    public SensorsData() {
    }

    public SensorsData(String sensorName, String description, String humidityCondition, String date, int humidity) {
        this.sensorName = sensorName;
        this.humidityCondition = humidityCondition;
        this.date = date;
        this.description = description;
        this.humidity = humidity;
    }

    public String getHumidityCondition() {
        return humidityCondition;
    }

    public void setHumidityCondition(String humidityCondition) {
        this.humidityCondition = humidityCondition;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
