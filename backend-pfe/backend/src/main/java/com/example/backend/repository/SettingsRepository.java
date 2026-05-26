package com.example.backend.repository;

import com.example.backend.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
}