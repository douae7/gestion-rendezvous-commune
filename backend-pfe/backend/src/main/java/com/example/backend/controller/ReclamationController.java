package com.example.backend.controller;

import com.example.backend.entity.Reclamation;
import com.example.backend.enums.StatutReclamation;
import com.example.backend.repository.ReclamationRepository;
import com.example.backend.service.ReclamationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reclamations")
public class ReclamationController {
    private final ReclamationRepository reclamationRepository;

    private final ReclamationService service;

    public ReclamationController(ReclamationService service , ReclamationRepository reclamationRepository ) {
        this.reclamationRepository = reclamationRepository;
        this.service = service;
    }

    @PostMapping
    public Reclamation create(@RequestBody Reclamation r) {
        return service.create(r);
    }
    @GetMapping("/user/{id}")
    public List<Reclamation> getByUser(@PathVariable Long id) {
        return service.getByUser(id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reclamationRepository.deleteById(id);
    }
    @PutMapping("/{id}")
    public Reclamation update(@PathVariable Long id, @RequestBody Reclamation r) {
        r.setId(id);
        return reclamationRepository.save(r);
    }
    @GetMapping
    public List<Reclamation> getAll() {
        return service.getAll();
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam StatutReclamation status
    ) {
        return ResponseEntity.ok(service.updateStatut(id, status));
    }
}
