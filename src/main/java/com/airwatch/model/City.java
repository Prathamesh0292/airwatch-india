package com.airwatch.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cities")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String state;

    @Column(name = "waqi_station_id")
    private String waqiStationId;

    private Double latitude;
    private Double longitude;

    @Column(name = "is_active")
    private boolean active = true;
}