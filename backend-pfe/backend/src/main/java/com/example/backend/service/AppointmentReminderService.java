package com.example.backend.service;

import com.example.backend.entity.Appointment;
import com.example.backend.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AppointmentReminderService {

    private final JavaMailSender mailSender;
    private final AppointmentRepository appointmentRepository;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm");

    public void sendReminder(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new RuntimeException("Rendez-vous introuvable : " + appointmentId)
                );

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime apptDateTime = appointment.getStartDateTime();

        if (apptDateTime == null) {
            throw new RuntimeException("Date du rendez-vous invalide");
        }

        // ✔ Calcul propre du temps restant
        long hoursUntil = Duration.between(now, apptDateTime).toHours();

        // ❌ déjà passé
        if (hoursUntil < 0) {
            throw new RuntimeException("Le rendez-vous est déjà passé.");
        }

        // ❌ trop loin (>24h)
        if (hoursUntil > 24) {
            throw new RuntimeException("Le rendez-vous n'est pas dans les 24 heures.");
        }

        // ── Sécurité des nulls ─────────────────────────────
        if (appointment.getCitizen() == null || appointment.getCitizen().getEmail() == null) {
            throw new RuntimeException("Email du citoyen introuvable");
        }

        String citizenEmail = appointment.getCitizen().getEmail();
        String citizenName = appointment.getCitizen().getName() != null
                ? appointment.getCitizen().getName()
                : "Citoyen";

        String serviceName = appointment.getServiceName() != null
                ? appointment.getServiceName()
                : "Service administratif";

        String dateStr = apptDateTime.format(FORMATTER);

        // ── Email ───────────────────────────────────────────
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(citizenEmail);
        message.setSubject("Rappel : votre rendez-vous");
        message.setText(
                "Bonjour " + citizenName + ",\n\n" +
                        "Ceci est un rappel de votre rendez-vous :\n\n" +
                        "📅 Date : " + dateStr + "\n" +
                        "📌 Service : " + serviceName + "\n\n" +
                        "Merci de vous présenter à l'heure.\n\n" +
                        "Cordialement,\nAdministration"
        );

        mailSender.send(message);
    }
}