package my.e.soilmoisturetemperarureproject.Model;

public class User {

    private String userId;
    private String userName;
    private String userEmail;
    private SensorsData sensorsData;

    public User() {
    }


    public User(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public User(String userName, String userEmail, String sensorName, String sensorDescription,
                String sensorMoistureCondition, String sensorTemperature) {
        this.userName = userName;
        this.userEmail = userEmail;
        sensorsData = new SensorsData(sensorName, sensorDescription, sensorMoistureCondition, sensorTemperature);

    }

    public User(String userName, String userEmail, String sensorDescription,
                String sensorMoistureCondition, String sensorTemperature) {
        this.userName = userName;
        this.userEmail = userEmail;
        sensorsData = new SensorsData(sensorDescription, sensorMoistureCondition, sensorTemperature);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public SensorsData getSensorsData() {
        return sensorsData;
    }

    public void setSensorsData(SensorsData sensorsData) {
        this.sensorsData = sensorsData;
    }
}
