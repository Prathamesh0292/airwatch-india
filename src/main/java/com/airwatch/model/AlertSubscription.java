package com.airwatch.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alert_subscriptions")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AlertSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(nullable = false)
    private Integer threshold = 150;

    @Column(name = "is_active")
    private boolean active = true;
}