package com.example.backend.service;

import com.example.backend.dto.ServiceCountDTO;
import com.example.backend.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final AppointmentRepository appointmentRepository;

    public DashboardService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Map<String, Integer> getAppointmentsByService() {

        List<ServiceCountDTO> results = appointmentRepository.countByService();

        Map<String, Integer> map = new HashMap<>();

        for (ServiceCountDTO r : results) {
            map.put(r.getService(), r.getCount().intValue());
        }

        return map;
    }
}