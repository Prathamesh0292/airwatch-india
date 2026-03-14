package com.airwatch.schedular;

import com.airwatch.model.AqiReading;
import com.airwatch.model.City;
import com.airwatch.repository.AqiReadingRepository;
import com.airwatch.repository.CityRepository;
import com.airwatch.service.AlertService;
import com.airwatch.service.WaqiApiService;
import com.example.airwatch.model.*;
import com.example.airwatch.repo.*;
import com.example.airwatch.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AqiScheduler {

    private final CityRepository cityRepository;
    private final AqiReadingRepository aqiReadingRepository;
    private final WaqiApiService waqiApiService;
    private final AlertService alertService;
    private final SimpMessagingTemplate messagingTemplate;

    // Runs every hour at the top of the hour
    @Scheduled(cron = "${aqi.scheduler.cron}")
    public void fetchAndStoreAqi() {
        log.info("Starting AQI fetch for all cities...");
        List<City> cities = cityRepository.findByActiveTrue();

        for (City city : cities) {
            AqiReading reading = waqiApiService.fetchReading(city);
            if (reading != null) {
                aqiReadingRepository.save(reading);
                log.info("Saved AQI {} for {}", reading.getAqi(),
                    city.getName());

                // Push live update to React dashboard
                messagingTemplate.convertAndSend(
                    "/topic/aqi/" + city.getId(), reading);

                // Trigger email alerts if threshold crossed
                alertService.checkAndAlert(reading);
            }
        }
        log.info("AQI fetch complete.");
    }
}