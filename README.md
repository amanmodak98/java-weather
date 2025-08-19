# Weather Forecast Application

A Spring Boot service that fetches real-time weather data and a 5-day forecast from OpenWeatherMap and exposes it via a simple REST API.

## Features
- Current temperature and humidity
- Aggregated 5-day forecast (min/max per day + dominant description)
- Simple REST endpoint: `GET /api/weather?city=London`

## Setup

1. Get an API key from https://openweathermap.org/api and export it:

```bash
export OPENWEATHER_API_KEY="<your_key>"
```

2. Build and run (requires Java 17 and Maven):

```bash
./mvnw spring-boot:run
```

Or using your system Maven:

```bash
mvn spring-boot:run
```

Then open:
```
http://localhost:8080/api/weather?city=London
```

## Response Shape
```json
{
  "location": "London",
  "temperatureC": 21.3,
  "humidity": 58.0,
  "forecast": [
    { "date": "2025-08-19", "minTempC": 18.1, "maxTempC": 24.5, "description": "light rain" }
  ]
}
```

## Notes
- Units are metric (Celsius) by default.
- Errors from the upstream API are mapped to JSON via a global handler.
- You can change the port in `src/main/resources/application.yml`.
