package com.example.backend.service;

import com.example.backend.entity.Appointment;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(String to, String subject, String body) {
        System.out.println("📧 EMAIL TO: " + to);
        System.out.println("SUBJECT: " + subject);
        System.out.println(body);

        // 👉 ici tu branches JavaMailSender plus tard
    }

}