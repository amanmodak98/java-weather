package com.codec.weather.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class OpenWeatherClient {

    private final RestClient restClient;
    private final String apiKey;

    public OpenWeatherClient(RestClient restClient,
                             @Value("${openweather.apiKey:}") String apiKey) {
        this.restClient = restClient;
        this.apiKey = apiKey;
    }

    public Map<String, Object> currentByCity(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={key}&units=metric";
        return restClient.get()
                .uri(url, city, apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Map.class);
    }

    public Map<String, Object> forecastByCity(String city) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={key}&units=metric";
        return restClient.get()
                .uri(url, city, apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Map.class);
    }
}
