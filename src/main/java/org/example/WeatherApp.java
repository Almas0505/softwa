package org.example;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class WeatherData {
    private int temperature;
    private String description;
    private int windChill;
    private double pressure;
    private int humidity;
    private double windSpeed;
    private int windDirection;
    private int cloudiness;
    private double visibility;

    public WeatherData() {}

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = (int) (temperature - 273.15);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWindChill() {
        return windChill;
    }

    public void setWindChill(int windChill) {
        this.windChill = windChill;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(int cloudiness) {
        this.cloudiness = cloudiness;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }
}

class WeatherDataAdapter {
    public static WeatherData adaptFromJson(JSONObject jsonResponse) {
        WeatherData weatherData = new WeatherData();

        weatherData.setTemperature(jsonResponse.getJSONObject("main").getInt("temp"));
        weatherData.setDescription(jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description"));
        weatherData.setWindChill(0);
        weatherData.setPressure(jsonResponse.getJSONObject("main").getDouble("pressure"));
        weatherData.setHumidity(jsonResponse.getJSONObject("main").getInt("humidity"));
        weatherData.setWindSpeed(jsonResponse.getJSONObject("wind").getDouble("speed"));
        weatherData.setWindDirection(jsonResponse.getJSONObject("wind").getInt("deg"));
        weatherData.setCloudiness(jsonResponse.getJSONObject("clouds").getInt("all"));
        weatherData.setVisibility(jsonResponse.getInt("visibility") / 1000.0);

        return weatherData;
    }
}

public class WeatherApp {
    private static final String API_KEY = "31314b4fa0621986d2a0d8361009365e";

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter city name: ");
            String city = reader.readLine();

            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = inputReader.readLine()) != null) {
                    response.append(inputLine);
                }
                inputReader.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                WeatherData weatherData = WeatherDataAdapter.adaptFromJson(jsonResponse);

                System.out.println("Temperature: " + weatherData.getTemperature() + "°C");
                System.out.println("Description: " + weatherData.getDescription());
                System.out.println("Wind Chill: " + weatherData.getWindChill() + "°C");
                System.out.println("Pressure: " + weatherData.getPressure() + " hPa");
                System.out.println("Humidity: " + weatherData.getHumidity() + "%");
                System.out.println("Wind Speed: " + weatherData.getWindSpeed() + " km/h");
                System.out.println("Wind Direction: " + weatherData.getWindDirection() + "°");
                System.out.println("Cloudiness: " + weatherData.getCloudiness() + "%");
                System.out.println("Visibility: " + weatherData.getVisibility() + " km");

            } else {
                System.out.println("Failed to retrieve weather data for the city: " + city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

