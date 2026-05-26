package com.example.backend.service;

import com.example.backend.entity.Appointment;
import com.example.backend.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AppointmentRepository appointmentRepository;

    // =========================
    // GET AGENT SCHEDULE BY DATE
    // =========================
    public List<Map<String, Object>> getAgentAvailability(Long agentId, String date) {

        LocalDate selectedDate = LocalDate.parse(date);

        LocalDateTime startOfDay = selectedDate.atStartOfDay();
        LocalDateTime endOfDay = selectedDate.atTime(LocalTime.MAX);

        // 🔥 récupérer tous les RDV de l’agent ce jour-là
        List<Appointment> appointments =
                appointmentRepository.findByAgentIdAndStartDateTimeBetween(
                        agentId,
                        startOfDay,
                        endOfDay
                );

        List<Map<String, Object>> result = new ArrayList<>();

        for (Appointment a : appointments) {

            Map<String, Object> map = new HashMap<>();

            map.put("id", a.getId());
            map.put("citizen", a.getCitizen() != null ? a.getCitizen().getName() : "N/A");
            map.put("service", a.getServiceName());
            map.put("status", a.getStatus());

            map.put("startDateTime", a.getStartDateTime());
            map.put("endDateTime", a.getEndDateTime());

            map.put("time", a.getTime());

            result.add(map);
        }

        return result;
    }
}