package com.airwatch.service;

import com.airwatch.model.City;
import com.airwatch.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<City> getAllCities() {
        return cityRepository.findByActiveTrue();
    }

    public City getCity(Long id) {
        return cityRepository.findById(id)
            .orElseThrow(() ->
                new RuntimeException("City not found: " + id));
    }
}