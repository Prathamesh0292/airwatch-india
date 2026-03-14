package com.airwatch.service;

import com.airwatch.model.*;
import com.airwatch.model.AqiReading.AqiCategory;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaqiApiService {

    private final RestTemplate restTemplate;

    @Value("${waqi.api.token}")
    private String token;

    @Value("${waqi.api.base-url}")
    private String baseUrl;

    public AqiReading fetchReading(City city) {
        try {
            String url = baseUrl + "/" + city.getWaqiStationId()
                       + "/?token=" + token;
            JsonNode root = restTemplate
                .getForObject(url, JsonNode.class);

            if (root == null || !"ok".equals(
                    root.path("status").asText())) {
                log.warn("Bad response for city: {}", city.getName());
                return null;
            }

            JsonNode data = root.path("data");
            JsonNode iaqi = data.path("iaqi");

            int aqiValue = data.path("aqi").asInt();

            return AqiReading.builder()
                .city(city)
                .recordedAt(LocalDateTime.now())
                .aqi(aqiValue)
                .pm25(getVal(iaqi, "pm25"))
                .pm10(getVal(iaqi, "pm10"))
                .co(getVal(iaqi, "co"))
                .humidity(getVal(iaqi, "h"))
                .temperature(getVal(iaqi, "t"))
                .category(AqiCategory.fromAqi(aqiValue))
                .build();

        } catch (Exception e) {
            log.error("Error fetching AQI for {}: {}",
                city.getName(), e.getMessage());
            return null;
        }
    }

    private Double getVal(JsonNode iaqi, String key) {
        JsonNode node = iaqi.path(key).path("v");
        return node.isMissingNode() ? null : node.asDouble();
    }
}