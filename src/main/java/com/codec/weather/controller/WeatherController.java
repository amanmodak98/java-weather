package com.codec.weather.controller;

import com.codec.weather.dto.WeatherResponse;
import com.codec.weather.service.WeatherService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@Validated
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public WeatherResponse byCity(@RequestParam(name = "city") @NotBlank String city) {
        return weatherService.getWeatherForCity(city);
    }
}
