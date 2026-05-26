package com.example.backend.service;

import com.example.backend.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ReportService {

    private final AppointmentRepository repo;

    public ReportService(AppointmentRepository repo) {
        this.repo = repo;
    }

    public Map<String, Integer> byService() {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (Object[] r : repo.count_ByService()) {
            map.put((String) r[0], ((Long) r[1]).intValue());
        }
        return map;
    }

    public Map<String, Integer> byStatus() {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (Object[] r : repo.countByStatus()) {
            map.put((String) r[0], ((Long) r[1]).intValue());
        }
        return map;
    }

    public Map<String, Integer> byMonth() {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (Object[] r : repo.countByMonth()) {
            map.put("M" + r[0], ((Long) r[1]).intValue());
        }
        return map;
    }

}