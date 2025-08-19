package com.codec.weather;

import com.codec.weather.client.OpenWeatherClient;
import com.codec.weather.dto.WeatherResponse;
import com.codec.weather.service.WeatherService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WeatherServiceTest {

    @Test
    void aggregatesForecastIntoDays() {
        OpenWeatherClient client = mock(OpenWeatherClient.class);
        WeatherService service = new WeatherService(client);

        when(client.currentByCity("Paris")).thenReturn(Map.of(
                "name", "Paris",
                "main", Map.of("temp", 20.0, "humidity", 50)
        ));

        // Two 3-hour slots same day, one next day
        List<Map<String, Object>> list = List.of(
                Map.of("dt", 1_600_000_000, "main", Map.of("temp_min", 18.0, "temp_max", 22.0),
                        "weather", List.of(Map.of("description", "clouds"))),
                Map.of("dt", 1_600_002_000, "main", Map.of("temp_min", 17.0, "temp_max", 23.0),
                        "weather", List.of(Map.of("description", "clouds"))),
                Map.of("dt", 1_600_086_400, "main", Map.of("temp_min", 15.0, "temp_max", 21.0),
                        "weather", List.of(Map.of("description", "rain")))
        );

        when(client.forecastByCity("Paris")).thenReturn(Map.of("list", list));

        WeatherResponse resp = service.getWeatherForCity("Paris");
        assertEquals("Paris", resp.location());
        assertEquals(20.0, resp.temperatureC());
        assertEquals(50.0, resp.humidity());
        assertTrue(resp.forecast().size() >= 2);
    }
}
