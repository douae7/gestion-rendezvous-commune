package com.example.backend.controller;

import com.example.backend.repository.AppointmentRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final DashboardService dashboardService;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    public AdminDashboardController(UserRepository userRepository,
                                    AppointmentRepository appointmentRepository,
                                    DashboardService dashboardService) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.dashboardService = dashboardService;
    }

    // ================= STATS =================
    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        Map<String, Object> stats = new HashMap<>();

        stats.put("citizens", userRepository.countCitizens());
        stats.put("agents", userRepository.countAgents());
        stats.put("todayAppointments", appointmentRepository.countTodayAppointments());

        return stats;
    }

    // ================= SERVICES CHART =================
    @GetMapping("/services")
    public Map<String, Integer> getServicesStats() {
        return dashboardService.getAppointmentsByService();
    }
}