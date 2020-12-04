package my.e.soilmoisturetemperarureproject.Model;

public class User {

    public String userId;
    public String userName;
    public String userEmail;
    public String sensorName;
    public Data sensorData;

    public User() {
    }

    public User(String userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public User(String userId, String userName, String userEmail, String sensorName) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.sensorName = sensorName;
    }

    public User(String userId, String userName, String userEmail, String sensorName,
                String humidityCondition, String sensorDescription, int humidity, float temperature) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.sensorName = sensorName;
        sensorData = new Data(humidityCondition, sensorDescription, humidity,temperature);
    }

    public User(String userId, String sensorName,
                String humidityCondition, String sensorDescription, int humidity, float temperature) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.sensorName = sensorName;
        sensorData = new Data(humidityCondition, sensorDescription, humidity,temperature);
    }



}
