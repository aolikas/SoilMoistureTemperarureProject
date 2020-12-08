package my.e.soilmoisturetemperarureproject.Model;

public class SensorsData {
    private String sensorName;
    private String sensorDescription;
    private String sensorMoistureCondition;
    private String sensorTemperature;

    public SensorsData() {
    }

    public SensorsData(String sensorDescription) {
        this.sensorDescription = sensorDescription;
    }

    public SensorsData(String sensorDescription, String sensorMoistureCondition, String sensorTemperature) {
        this.sensorDescription = sensorDescription;
        this.sensorMoistureCondition = sensorMoistureCondition;
        this.sensorTemperature = sensorTemperature;
    }

    public SensorsData(String sensorName, String sensorDescription, String sensorMoistureCondition, String sensorTemperature) {
        this.sensorName = sensorName;
        this.sensorDescription = sensorDescription;
        this.sensorMoistureCondition = sensorMoistureCondition;
        this.sensorTemperature = sensorTemperature;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorDescription() {
        return sensorDescription;
    }

    public void setSensorDescription(String sensorDescription) {
        this.sensorDescription = sensorDescription;
    }

    public String getSensorMoistureCondition() {
        return sensorMoistureCondition;
    }

    public void setSensorMoistureCondition(String sensorMoistureCondition) {
        this.sensorMoistureCondition = sensorMoistureCondition;
    }

    public String getSensorTemperature() {
        return sensorTemperature;
    }

    public void setSensorTemperature(String sensorTemperature) {
        this.sensorTemperature = sensorTemperature;
    }
}
