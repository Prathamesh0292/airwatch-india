package com.airwatch.service;

import com.airwatch.model.AlertSubscription;
import com.airwatch.model.AqiReading;
import com.airwatch.model.*;
import com.airwatch.repository.AlertSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertService {

    private final AlertSubscriptionRepository subscriptionRepository;
    private final JavaMailSender mailSender;

    public void checkAndAlert(AqiReading reading) {
        if (reading.getAqi() == null) return;

        List<AlertSubscription> subs = subscriptionRepository
            .findByCityAndActiveTrue(reading.getCity());

        for (AlertSubscription sub : subs) {
            if (reading.getAqi() >= sub.getThreshold()) {
                sendAlert(sub.getEmail(), reading);
            }
        }
    }

    private void sendAlert(String email, AqiReading reading) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("AQI Alert: " +
                reading.getCity().getName() +
                " — AQI is " + reading.getAqi());
            helper.setText(buildEmailBody(reading), true);

            mailSender.send(message);
            log.info("Alert sent to {} for city {}",
                email, reading.getCity().getName());

        } catch (Exception e) {
            log.error("Failed to send alert email: {}",
                e.getMessage());
        }
    }

    private String buildEmailBody(AqiReading reading) {
        return """
            <h2>AQI Alert — %s</h2>
            <p>Current AQI: <strong>%d</strong> (%s)</p>
            <p>PM2.5: %.1f | PM10: %.1f | Humidity: %.1f%%</p>
            <p><em>%s</em></p>
            <p>Stay safe! — AirWatch India</p>
            """.formatted(
                reading.getCity().getName(),
                reading.getAqi(),
                reading.getCategory(),
                reading.getPm25() != null ? reading.getPm25() : 0,
                reading.getPm10() != null ? reading.getPm10() : 0,
                reading.getHumidity() != null ? reading.getHumidity() : 0,
                com.airwatch.dto.response.AqiResponse
                    .getHealthAdvice(reading.getAqi())
            );
    }
}