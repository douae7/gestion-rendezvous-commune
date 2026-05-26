package com.example.backend.service;

import com.example.backend.entity.Appointment;
import com.example.backend.entity.Reclamation;
import com.example.backend.enums.NotificationType;
import com.example.backend.enums.StatutReclamation;
import com.example.backend.repository.AppointmentRepository;
import com.example.backend.repository.ReclamationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.backend.enums.StatutReclamation.EN_COURS;

@Service
public class ReclamationService {

    private final ReclamationRepository repository;
    private final AppointmentRepository appointmentRepository;
    private final NotificationService notificationService; // ← injecter


    public ReclamationService(ReclamationRepository repository,NotificationService notificationService, AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public Reclamation create(Reclamation req) {

        // ✅ BON
        Appointment a = appointmentRepository
                .findById(req.getAppointment().getId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Reclamation r = new Reclamation();
        r.setSubject(req.getSubject());
        r.setMessage(req.getMessage());
        r.setStatus("En_cours");
        r.setCreatedAt(LocalDateTime.now());

        r.setAppointment(a); // ✅ entity managed
        r.setUser(a.getCitizen());
        Reclamation saved = repository.save(r);

        // 🔥 1. ADMIN (EXISTANT)
        notificationService.notifyRole(
                "ADMIN",
                "🆕 Nouvelle réclamation #" + saved.getId(),
                "De " + a.getCitizen().getName() + " sur RDV #" + a.getId(),
                NotificationType.RECLAMATION
        );

        // 🔥 2. CITOYEN ← NOUVEAU !
        notificationService.notifyUser(
                a.getCitizen(),
                "📝 Réclamation #" + saved.getId() + " créée",
                "Votre réclamation a été enregistrée et sera traitée.",
                NotificationType.RECLAMATION
        );
        return saved;
    }

    public List<Reclamation> getAll() {
        return repository.findAll();
    }

    public List<Reclamation> getByUser(Long userId) {
        return repository.findByUserId(userId);
    }





    // ReclamationService.java
    public Reclamation updateStatut(Long reclamationId, StatutReclamation nouveauStatut) {
        Reclamation reclamation = repository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Réclamation introuvable"));

        reclamation.setStatus(nouveauStatut.name());
        repository.save(reclamation);

        String message = switch (nouveauStatut) {
            case EN_COURS -> "Votre réclamation #" + reclamation.getId() + " est en cours.";
            case RESOLUE -> "Votre réclamation #" + reclamation.getId() + " est résolue !";
            case REJETEE -> "Votre réclamation #" + reclamation.getId() + " a été rejetée.";
            default -> "Statut réclamation #" + reclamation.getId() + " mis à jour.";
        };

        // 🔥 1. CITOYEN
        if (reclamation.getUser() != null) {
            notificationService.notifyUser(
                    reclamation.getUser(),
                    "📝 Réclamation #" + reclamation.getId() + " : " + nouveauStatut.name(),
                    message,
                    NotificationType.RECLAMATION
            );
        }

        // 🔥 2. AGENTS
        notificationService.notifyRole(
                "AGENT",
                "Réclamation #" + reclamation.getId() + " mise à jour",
                "Réclamation " + reclamation.getUser().getName() + " : " + nouveauStatut.name(),
                NotificationType.RECLAMATION
        );

        return reclamation;
    }
}
