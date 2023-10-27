package org.example;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
                JSONObject main = jsonResponse.getJSONObject("main");
                JSONObject wind = jsonResponse.getJSONObject("wind");
                JSONObject clouds = jsonResponse.getJSONObject("clouds");

                double temperatureKelvin = main.getDouble("temp");


                int temperatureCelsius = (int) (temperatureKelvin - 273.15);
                double pressure = main.getDouble("pressure");
                int humidity = main.getInt("humidity");
                double windSpeedMs = wind.getDouble("speed");
                double windSpeedKmh = windSpeedMs * 3.6;
                int windDirection = wind.getInt("deg");
                int cloudiness = clouds.getInt("all");
                int visibilityMeters = jsonResponse.getInt("visibility");
                double visibilityKm = visibilityMeters / 1000.0;
                String description = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
                double windChillIndex = 13.12 + 0.6215 * temperatureCelsius - 11.37 * Math.pow(windSpeedMs, 0.16) + 0.3965 * temperatureCelsius * Math.pow(windSpeedMs, 0.16);


                int windChillTemperature = (int) windChillIndex;

                System.out.println();
                System.out.println("Temperature: " + temperatureCelsius + "°C");
                System.out.println("Description: " + description);
                System.out.println("Wind Chill: " + windChillTemperature + "°C");
                System.out.println("Pressure: " + pressure + " hPa");
                System.out.println("Humidity: " + humidity + "%");
                System.out.println("Wind Speed: " + windSpeedKmh + " km/h");
                System.out.println("Wind Direction: " + windDirection + "°");
                System.out.println("Cloudiness: " + cloudiness + "%");
                System.out.println("Visibility: " + visibilityKm + " km");

            } else {
                System.out.println("Failed to retrieve weather data for the city: " + city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
