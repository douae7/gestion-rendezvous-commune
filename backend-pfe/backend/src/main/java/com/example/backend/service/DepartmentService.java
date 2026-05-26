package com.example.backend.service;

import com.example.backend.entity.Department;
import com.example.backend.entity.User;
import com.example.backend.repository.DepartmentRepository;
import com.example.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;
    private final UserRepository userRepository;

    // ================= GET ALL =================
    public List<Department> getAll() {
        return repository.findAll();
    }

    // ================= CREATE =================
    // ================= CREATE =================
    public Department create(Department d, MultipartFile file) {
        boolean exists = repository.existsByNameIgnoreCase(d.getName());

        if (exists) {
            throw new RuntimeException("Ce service existe déjà !");
        }

        if (d.getHeadAgent() != null && d.getHeadAgent().getId() != null) {
            User agent = userRepository.findById(d.getHeadAgent().getId())
                    .orElseThrow(() -> new RuntimeException("Agent introuvable"));
            d.setHeadAgent(agent);
        }

        if (file != null && !file.isEmpty()) {

            String uploadDir = System.getProperty("user.dir") + "/uploads/departments/";

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String path = uploadDir + fileName;

            try {
                file.transferTo(new File(path));
            } catch (Exception e) {
                throw new RuntimeException("Upload failed: " + e.getMessage());
            }

            d.setDocumentUrl("departments/" + fileName);
        }

        return repository.save(d);
    }
    // ================= UPDATE =================
    public Department update(
            Long id,
            String name,
            String description,
            String phone,
            boolean active,
            Long headAgentId,
            MultipartFile file
    ) {

        Department dep = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));


        dep.setName(name);
        dep.setDescription(description);
        dep.setPhone(phone);
        dep.setActive(active);
        boolean exists = repository.existsByNameIgnoreCase(dep.getName());

        if (exists && !repository.findById(dep.getId()).get().getName().equalsIgnoreCase(dep.getName())) {
            throw new RuntimeException("Ce service existe déjà !");
        }

        // ===== HEAD AGENT =====
        if (headAgentId != null) {
            User agent = userRepository.findById(headAgentId)
                    .orElseThrow(() -> new RuntimeException("Agent introuvable"));
            dep.setHeadAgent(agent);
        } else {
            dep.setHeadAgent(null);
        }

        // ===== FILE UPLOAD FIXED =====
        if (file != null && !file.isEmpty()) {

            try {
                // 🔥 CHEMIN ABSOLU (IMPORTANT FIX)
                String uploadDir = System.getProperty("user.dir")
                        + "/uploads/departments/";

                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String fileName = System.currentTimeMillis()
                        + "_" + file.getOriginalFilename();

                File destination = new File(uploadDir + fileName);
                file.transferTo(destination);

                // 🔥 URL RELATIVE (FRONTEND FRIENDLY)
                dep.setDocumentUrl("departments/" + fileName);

            } catch (Exception e) {
                throw new RuntimeException("Upload failed: " + e.getMessage());
            }
        }

        return repository.save(dep);
    }

    // ================= DELETE =================
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // ================= TOGGLE =================
    public Department toggle(Long id) {

        Department dep = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        dep.setActive(!dep.isActive());
        return repository.save(dep);
    }

    // DepartmentService.java
    public Optional<Department> findByNameContainingIgnoreCase(String userInput) {

        // Normaliser l'input utilisateur
        String normalized = Normalizer.normalize(userInput, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .trim();

        // Chercher tous les depts, puis filtrer côté Java
        return repository.findAll()
                .stream()
                .filter(dept -> {
                    String deptNorm = Normalizer.normalize(dept.getName(), Normalizer.Form.NFD)
                            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                            .toLowerCase();
                    return deptNorm.contains(normalized);
                })
                .findFirst();
    }
    @Transactional
    public void toggleDepartment(Long id) {

        Department dep = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // ================= TOGGLE =================
        boolean newStatus = !dep.isActive();
        dep.setActive(newStatus);

        String agentStatus = newStatus ? "active" : "inactive";

        // ================= UPDATE USERS DIRECT SQL =================
        userRepository.updateStatusByDepartementId(id, agentStatus);

        // ================= SAVE DEPARTMENT =================
        repository.save(dep);
    }
}