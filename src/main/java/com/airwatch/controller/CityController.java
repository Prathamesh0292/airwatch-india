package com.airwatch.controller;

import com.airwatch.dto.response.ApiResponse;
import com.airwatch.model.City;
import com.airwatch.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ApiResponse<List<City>> getAll() {
        return ApiResponse.success(cityService.getAllCities());
    }

    @GetMapping("/{id}")
    public ApiResponse<City> getOne(@PathVariable Long id) {
        return ApiResponse.success(cityService.getCity(id));
    }
}