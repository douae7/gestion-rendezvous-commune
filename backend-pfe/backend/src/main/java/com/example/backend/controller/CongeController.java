package com.example.backend.controller;

import com.example.backend.dto.CongeRequest;
import com.example.backend.entity.Conge;
import com.example.backend.entity.User;
import com.example.backend.repository.CongeRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conges")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CongeController {

    private final CongeRepository congeRepository;
    private final UserRepository userRepository;

    // ✅ CREATE CONGE
    @PostMapping
    public Conge create(@RequestBody CongeRequest request) {

        if (request.getAgentId() == null) {
            throw new RuntimeException("agentId required");
        }

        User agent = userRepository.findById(request.getAgentId())
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        Conge conge = new Conge();
        conge.setAgent(agent);
        conge.setStartDate(request.getStartDate());
        conge.setEndDate(request.getEndDate());
        conge.setType(request.getType());

        return congeRepository.save(conge);
    }

    @DeleteMapping("/{id}")
    public void deleteConge(@PathVariable Long id) {
        congeRepository.deleteById(id);
    }

    @GetMapping("/agent/{id}")
    public List<Conge> getByAgent(@PathVariable Long id) {
        return congeRepository.findByAgentId(id);
    }

    @PutMapping("/{id}")
    public Conge updateConge(@PathVariable Long id, @RequestBody CongeRequest request) {

        Conge conge = congeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conge not found"));

        if (request.getStartDate() != null) {
            conge.setStartDate(request.getStartDate());
        }

        if (request.getEndDate() != null) {
            conge.setEndDate(request.getEndDate());
        }

        if (request.getType() != null) {
            conge.setType(request.getType());
        }

        return congeRepository.save(conge);
    }
}