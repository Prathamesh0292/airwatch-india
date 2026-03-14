package com.airwatch.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AqiResponse {
    private Long cityId;
    private String cityName;
    private String state;
    private Double latitude;
    private Double longitude;
    private Integer aqi;
    private Double pm25;
    private Double pm10;
    private Double co;
    private Double humidity;
    private Double temperature;
    private String category;
    private String healthAdvice;
    private LocalDateTime recordedAt;

    public static String getHealthAdvice(int aqi) {
        if (aqi <= 50)  return "Air quality is good. Enjoy outdoor activities.";
        if (aqi <= 100) return "Air quality is acceptable. Sensitive people should limit prolonged outdoor exertion.";
        if (aqi <= 150) return "Sensitive groups should reduce outdoor activity.";
        if (aqi <= 200) return "Everyone should reduce prolonged outdoor exertion.";
        if (aqi <= 300) return "Everyone should avoid outdoor activity.";
        return "Health alert! Everyone should avoid all outdoor activity.";
    }
}