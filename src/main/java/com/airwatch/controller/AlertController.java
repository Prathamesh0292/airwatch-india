package com.airwatch.controller;

import com.airwatch.dto.request.SubscribeRequest;
import com.airwatch.dto.response.ApiResponse;
import com.airwatch.model.AlertSubscription;
import com.airwatch.model.City;
import com.airwatch.repository.AlertSubscriptionRepository;
import com.example.airwatch.repo.*;
import com.airwatch.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlertController {

    private final AlertSubscriptionRepository subscriptionRepository;
    private final CityService cityService;

    @PostMapping("/subscribe")
    public ApiResponse<String> subscribe(
            @Valid @RequestBody SubscribeRequest req) {
        City city = cityService.getCity(req.getCityId());
        AlertSubscription sub = AlertSubscription.builder()
            .email(req.getEmail())
            .city(city)
            .threshold(req.getThreshold())
            .active(true)
            .build();
        subscriptionRepository.save(sub);
        return ApiResponse.success(
            "Subscribed successfully! You'll be alerted when AQI in "
            + city.getName() + " crosses " + req.getThreshold(), null);
    }
}