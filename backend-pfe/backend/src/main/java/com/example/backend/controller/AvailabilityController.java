package com.example.backend.controller;

import com.example.backend.dto.AvailabilityRequest;
import com.example.backend.dto.SlotDTO;
import com.example.backend.entity.User;
import com.example.backend.repository.AppointmentRepository;
import com.example.backend.repository.CongeRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;

@RestController
@RequestMapping("/api/availability")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AvailabilityController {

    private final UserRepository userRepo;
    private final AppointmentRepository appointmentRepository;
    private final CongeRepository congeRepository;

    @GetMapping("/auto")
    public List<Map<String, Object>> getAutoSlots(
            @RequestParam Long departmentId,
            @RequestParam String date
    ) {
        List<User> agents = userRepo.findByDepartement_Id(departmentId);
        LocalDate selectedDate = LocalDate.parse(date);

        System.out.println("=== AUTO SLOTS ===");
        System.out.println("departmentId = " + departmentId);
        System.out.println("date = " + selectedDate);
        System.out.println("agents count = " + agents.size());

        List<LocalTime[]> ranges = List.of(
                new LocalTime[]{LocalTime.of(8, 0), LocalTime.of(13, 0)},
                new LocalTime[]{LocalTime.of(14, 0), LocalTime.of(17, 0)}
        );

        List<Map<String, Object>> result = new ArrayList<>();

        for (LocalTime[] range : ranges) {
            for (LocalTime time = range[0]; time.isBefore(range[1]); time = time.plusMinutes(15)) {

                LocalDateTime start = LocalDateTime.of(selectedDate, time);
                LocalDateTime end   = start.plusMinutes(15);

                System.out.println("--- Slot " + time + " ---");

                long availableAgents = agents.stream()
                        .filter(agent -> {
                            boolean conflict = appointmentRepository.existsConflict(
                                    agent.getId(), start, end
                            );

                            boolean onLeave = congeRepository.isAgentOnLeave(
                                    agent.getId(), selectedDate
                            );

                            System.out.println("  agent " + agent.getId()
                                    + " conflict=" + conflict
                                    + " onLeave=" + onLeave);

                            return !conflict && !onLeave;
                        })
                        .count();

                System.out.println("  availableAgents = " + availableAgents);

                Map<String, Object> slot = new HashMap<>();
                slot.put("time", time.toString());
                slot.put("availableAgents", availableAgents);
                result.add(slot);
            }
        }

        return result;
    }
}