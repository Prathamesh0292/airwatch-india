package com.airwatch.controller;

import com.airwatch.dto.response.ApiResponse;
import com.airwatch.dto.response.AqiResponse;
import com.example.airwatch.dto.response.*;
import com.airwatch.service.AqiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/aqi")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AqiController {

    private final AqiService aqiService;

    // GET /api/aqi/all — latest AQI for all cities (for map)
    @GetMapping("/all")
    public ApiResponse<List<AqiResponse>> getAll() {
        return ApiResponse.success(aqiService.getLatestAll());
    }

    // GET /api/aqi/current/1
    @GetMapping("/current/{cityId}")
    public ApiResponse<AqiResponse> getCurrent(
            @PathVariable Long cityId) {
        return ApiResponse.success(aqiService.getCurrentAqi(cityId));
    }

    // GET /api/aqi/history/1?days=7
    @GetMapping("/history/{cityId}")
    public ApiResponse<List<AqiResponse>> getHistory(
            @PathVariable Long cityId,
            @RequestParam(defaultValue = "7") int days) {
        return ApiResponse.success(aqiService.getHistory(cityId, days));
    }

    // GET /api/aqi/compare?cities=1,2,3&days=7
    @GetMapping("/compare")
    public ApiResponse<List<AqiResponse>> compare(
            @RequestParam List<Long> cities,
            @RequestParam(defaultValue = "7") int days) {
        return ApiResponse.success(aqiService.compare(cities, days));
    }
}