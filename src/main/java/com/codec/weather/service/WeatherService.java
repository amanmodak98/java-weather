package com.codec.weather.service;

import com.codec.weather.client.OpenWeatherClient;
import com.codec.weather.dto.WeatherResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final OpenWeatherClient client;

    public WeatherService(OpenWeatherClient client) {
        this.client = client;
    }

    public WeatherResponse getWeatherForCity(String city) {
        Map<String, Object> current = client.currentByCity(city);
        Map<String, Object> forecast = client.forecastByCity(city);

        String location = Optional.ofNullable(current.get("name")).map(Object::toString).orElse(city);

        Map<String, Object> main = (Map<String, Object>) current.getOrDefault("main", Map.of());
        double temp = toDouble(main.get("temp"));
        double humidity = toDouble(main.get("humidity"));

        List<Map<String, Object>> list = (List<Map<String, Object>>) forecast.getOrDefault("list", List.of());
        Map<LocalDate, List<Map<String, Object>>> byDate = list.stream().collect(Collectors.groupingBy(item -> {
            Number dt = (Number) item.get("dt");
            return Instant.ofEpochSecond(dt.longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
        }));

        List<WeatherResponse.DailyForecast> daily = byDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .limit(5)
                .map(e -> {
                    LocalDate date = e.getKey();
                    double min = e.getValue().stream().map(m -> (Map<String, Object>) m.get("main")).filter(Objects::nonNull).mapToDouble(mm -> toDouble(mm.get("temp_min"))).min().orElse(Double.NaN);
                    double max = e.getValue().stream().map(m -> (Map<String, Object>) m.get("main")).filter(Objects::nonNull).mapToDouble(mm -> toDouble(mm.get("temp_max"))).max().orElse(Double.NaN);
                    String desc = e.getValue().stream()
                            .map(m -> (List<Map<String, Object>>) m.get("weather"))
                            .filter(Objects::nonNull)
                            .flatMap(Collection::stream)
                            .map(w -> Objects.toString(w.get("description"), null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                            .entrySet().stream().max(Map.Entry.comparingByValue())
                            .map(Map.Entry::getKey).orElse("");
                    return new WeatherResponse.DailyForecast(date, min, max, desc);
                })
                .toList();

        return new WeatherResponse(location, temp, humidity, daily);
    }

    private double toDouble(Object o) {
        if (o == null) return Double.NaN;
        if (o instanceof Number n) return n.doubleValue();
        try { return Double.parseDouble(o.toString()); } catch (Exception e) { return Double.NaN; }
    }
}
