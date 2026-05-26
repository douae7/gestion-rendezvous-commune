package com.example.backend.controller;
import com.example.backend.dto.AgentDTO;
import com.example.backend.dto.PasswordDTO;
import com.example.backend.entity.Appointment;
import com.example.backend.entity.Department;
import com.example.backend.entity.User;
import com.example.backend.repository.AppointmentRepository;
import com.example.backend.repository.DepartmentRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping("/api/agent")
@CrossOrigin(origins = "http://localhost:5173")
public class AgentDashboardController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // 📌 FILE D'ATTENTE
    @GetMapping("/queue/today")
    public List<Appointment> getQueue() {
        return appointmentRepository.findByDateAndStatus(
                LocalDate.now(),
                "waiting"
        );
    }

    // 📌 PLANNING AGENT
    @GetMapping("/schedule/today/{agentId}")
    public List<Appointment> getSchedule(@PathVariable Long agentId) {

        User agent = new User();
        agent.setId(agentId);

        return appointmentRepository.findByDateAndAgent(
                LocalDate.now(),
                agent
        );
    }

    // 📌 APPELER CITOYEN
    @PutMapping("/queue/{id}/call")
    public Appointment callCitizen(@PathVariable Long id) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow();

        appt.setStatus("in_progress");

        return appointmentRepository.save(appt);
    }
}