package com.example.backend.service;

import com.example.backend.entity.Appointment;
import com.example.backend.repository.AppointmentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class AppointmentScheduler {

    private final AppointmentRepository repository;

    public AppointmentScheduler(AppointmentRepository repository) {
        this.repository = repository;
    }

    // chaque 10 minutes
    @Scheduled(fixedRate = 600000)
    public void cancelExpiredAppointments() {

        LocalDateTime limit = LocalDateTime.now().minusHours(24);

        List<Appointment> expired = repository.findExpiredAppointments(limit);

        for (Appointment a : expired) {
            a.setStatus("cancelled");
        }

        repository.saveAll(expired);

        if (!expired.isEmpty()) {
            System.out.println("RDV annulés automatiquement: " + expired.size());
        }
    }
}