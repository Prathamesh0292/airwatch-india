package com.airwatch.repository;

import com.airwatch.model.AqiReading;
import com.airwatch.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AqiReadingRepository extends JpaRepository<AqiReading, Long> {

    // Latest reading for a city
    Optional<AqiReading> findTopByCityOrderByRecordedAtDesc(City city);

    // Readings within a date range
    List<AqiReading> findByCityAndRecordedAtBetweenOrderByRecordedAtAsc(
        City city, LocalDateTime from, LocalDateTime to);

    // Latest reading for multiple cities (for map)
    @Query("""
        SELECT a FROM AqiReading a
        WHERE a.recordedAt = (
            SELECT MAX(a2.recordedAt) FROM AqiReading a2
            WHERE a2.city = a.city
        )
    """)
    List<AqiReading> findLatestForAllCities();

    // For compare endpoint
    @Query("""
        SELECT a FROM AqiReading a
        WHERE a.city.id IN :cityIds
        AND a.recordedAt >= :from
        ORDER BY a.city.id, a.recordedAt ASC
    """)
    List<AqiReading> findByCityIdsAndDateAfter(
        @Param("cityIds") List<Long> cityIds,
        @Param("from") LocalDateTime from);
}