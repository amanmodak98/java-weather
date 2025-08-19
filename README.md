# ☀️ Java Weather — Spring Boot + Modern UI

A clean Spring Boot API with a modern, lightweight UI to view current conditions and a 5‑day forecast, powered by OpenWeatherMap.

## ✨ Highlights
- Current temperature and humidity
- Smart 5‑day forecast (min/max per day + dominant description)
- Pretty, responsive UI with subtle animations
- Single endpoint API: `GET /api/weather?city=London`

## 🚀 Quick start
Prereqs: Java 17+, Maven (or the included wrapper)

1) Get an API key from OpenWeatherMap and export it
```bash
export OPENWEATHER_API_KEY="<your_key>"
```

2) Run the app
```bash
./mvnw spring-boot:run
```

The UI will be available at:
```
http://localhost:8080/
```

Try the API directly:
```
http://localhost:8080/api/weather?city=London
```

Tip: If 8080 is busy, run on 8081
```bash
./mvnw -Dspring-boot.run.arguments="--server.port=8081" spring-boot:run
```

## 🖥️ UI preview
The landing page features a search bar, a hero card for current conditions, and a grid for the 5‑day forecast.

Screenshots (placeholders):
- Home: docs/screenshot-home.png
- Forecast: docs/screenshot-forecast.png

## 📦 Response shape
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

## ⚙️ Configuration
- Units: metric (Celsius) by default
- API key is read from env var `OPENWEATHER_API_KEY`
- Server port can be configured via `src/main/resources/application.yml` or run args

## 🧪 Tests
Run unit tests:
```bash
./mvnw test
```

## ☁️ Deploy (optional)
- Dockerize with Spring Boot build image plugin
- Deploy to Render/Fly.io/Heroku or any Java host

## 📄 License
MIT (or your preferred license) — feel free to use and adapt.
