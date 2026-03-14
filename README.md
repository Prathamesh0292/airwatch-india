# airwatch-india
# AirWatch India

> Real-time air quality monitoring and alert dashboard for Indian cities

![Dashboard Screenshot](screenshots/dashboard.png)

## Problem
Most Indian cities lack an accessible, unified view of AQI data.
People in cities like Nagpur have no easy way to track pollution
trends or get alerts when air quality becomes dangerous.

## Solution
AirWatch India aggregates real-time AQI data from the WAQI API
for 8 major Indian cities, stores historical readings, visualises
trends on an interactive map, and sends email alerts when AQI
crosses dangerous thresholds.

## Tech Stack
- **Backend:** Java 17, Spring Boot 3, Spring Security, JWT
- **Scheduler:** Spring @Scheduled (hourly WAQI API polling)
- **Database:** MySQL + Flyway migrations
- **Frontend:** React, Recharts, Leaflet.js
- **Deployment:** Docker + Render

## Features
- Live AQI map of India with color-coded city markers
- 7-day and 30-day historical trend charts
- Side-by-side city comparison
- Email alerts when AQI crosses user-set threshold
- JWT-authenticated admin panel

## Architecture
[Brief description or diagram]

## Setup
1. Clone the repo
2. Copy `application.properties.example` to `application.properties`
3. Fill in your DB credentials and WAQI API token
4. Run `docker-compose up`
5. Visit `http://localhost:8080`

## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/aqi/current/{cityId} | Latest AQI for a city |
| GET | /api/aqi/history/{cityId}?days=7 | Historical readings |
| GET | /api/aqi/compare?cities=1,2,3 | Compare cities |
| POST | /api/alerts/subscribe | Subscribe to alerts |

## Screenshots
[Add screenshots once built]

## Live Demo
[Add Render URL once deployed]
