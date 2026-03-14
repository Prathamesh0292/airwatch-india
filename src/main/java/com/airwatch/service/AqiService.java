package com.airwatch.service;

import com.airwatch.dto.response.AqiResponse;
import com.airwatch.model.*;
import com.airwatch.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AqiService {

    private final AqiReadingRepository aqiReadingRepository;
    private final CityRepository cityRepository;

    public AqiResponse getCurrentAqi(Long cityId) {
        City city = cityRepository.findById(cityId)
            .orElseThrow(() ->
                new RuntimeException("City not found: " + cityId));
        AqiReading reading = aqiReadingRepository
            .findTopByCityOrderByRecordedAtDesc(city)
            .orElseThrow(() ->
                new RuntimeException("No data for city: " + cityId));
        return toResponse(reading);
    }

    public List<AqiResponse> getHistory(Long cityId, int days) {
        City city = cityRepository.findById(cityId)
            .orElseThrow(() ->
                new RuntimeException("City not found: " + cityId));
        LocalDateTime from = LocalDateTime.now().minusDays(days);
        return aqiReadingRepository
            .findByCityAndRecordedAtBetweenOrderByRecordedAtAsc(
                city, from, LocalDateTime.now())
            .stream().map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<AqiResponse> getLatestAll() {
        return aqiReadingRepository.findLatestForAllCities()
            .stream().map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<AqiResponse> compare(List<Long> cityIds, int days) {
        LocalDateTime from = LocalDateTime.now().minusDays(days);
        return aqiReadingRepository
            .findByCityIdsAndDateAfter(cityIds, from)
            .stream().map(this::toResponse)
            .collect(Collectors.toList());
    }

    private AqiResponse toResponse(AqiReading r) {
        return AqiResponse.builder()
            .cityId(r.getCity().getId())
            .cityName(r.getCity().getName())
            .state(r.getCity().getState())
            .latitude(r.getCity().getLatitude())
            .longitude(r.getCity().getLongitude())
            .aqi(r.getAqi())
            .pm25(r.getPm25())
            .pm10(r.getPm10())
            .co(r.getCo())
            .humidity(r.getHumidity())
            .temperature(r.getTemperature())
            .category(r.getCategory() != null ?
                r.getCategory().name() : null)
            .healthAdvice(r.getAqi() != null ?
                AqiResponse.getHealthAdvice(r.getAqi()) : null)
            .recordedAt(r.getRecordedAt())
            .build();
    }
}