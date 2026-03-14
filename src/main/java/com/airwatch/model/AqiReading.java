package com.airwatch.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aqi_readings",
       indexes = @Index(columnList = "city_id, recorded_at"))
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AqiReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    private Integer aqi;
    private Double pm25;
    private Double pm10;
    private Double co;
    private Double humidity;
    private Double temperature;

    @Enumerated(EnumType.STRING)
    private AqiCategory category;

    public enum AqiCategory {
        GOOD, MODERATE, UNHEALTHY_SENSITIVE,
        UNHEALTHY, VERY_UNHEALTHY, HAZARDOUS;

        public static AqiCategory fromAqi(int aqi) {
            if (aqi <= 50)  return GOOD;
            if (aqi <= 100) return MODERATE;
            if (aqi <= 150) return UNHEALTHY_SENSITIVE;
            if (aqi <= 200) return UNHEALTHY;
            if (aqi <= 300) return VERY_UNHEALTHY;
            return HAZARDOUS;
        }
    }
}