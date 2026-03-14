package com.airwatch.repository;

import com.airwatch.model.AlertSubscription;
import com.airwatch.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertSubscriptionRepository
        extends JpaRepository<AlertSubscription, Long> {

    List<AlertSubscription> findByCityAndActiveTrue(City city);
}