package com.example.backend.service;

import com.example.backend.entity.Settings;
import com.example.backend.repository.SettingsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsService {

    private final SettingsRepository settingsRepository;

    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    // GET SETTINGS
    public Settings getSettings() {

        Optional<Settings> optional = settingsRepository.findById(1L);

        // créer automatiquement si vide
        if (optional.isEmpty()) {

            Settings settings = new Settings();

            settings.setName("");
            settings.setAddress("");
            settings.setPhone("");
            settings.setEmail("");

            return settingsRepository.save(settings);
        }

        return optional.get();
    }

    // UPDATE SETTINGS
    public Settings update(Settings newSettings) {

        Settings settings = getSettings();

        settings.setName(newSettings.getName());
        settings.setAddress(newSettings.getAddress());
        settings.setPhone(newSettings.getPhone());
        settings.setEmail(newSettings.getEmail());

        return settingsRepository.save(settings);
    }

    // DELETE
    public void delete() {

        settingsRepository.findById(1L)
                .ifPresent(settingsRepository::delete);
    }
}