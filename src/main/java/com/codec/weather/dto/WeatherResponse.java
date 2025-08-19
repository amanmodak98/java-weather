package com.codec.weather.dto;

import java.time.LocalDate;
import java.util.List;

public record WeatherResponse(
        String location,
        double temperatureC,
        double humidity,
        List<DailyForecast> forecast
) {
    public record DailyForecast(LocalDate date, double minTempC, double maxTempC, String description) { }
}
