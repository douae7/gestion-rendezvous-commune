package com.example.backend.controller;

import com.example.backend.entity.Settings;
import com.example.backend.service.SettingsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@CrossOrigin("*")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    // GET
    @GetMapping
    public Settings getSettings() {
        return settingsService.getSettings();
    }

    // UPDATE
    @PutMapping
    public Settings updateSettings(@RequestBody Settings settings) {
        return settingsService.update(settings);
    }

    // DELETE
    @DeleteMapping
    public void deleteSettings() {
        settingsService.delete();
    }
}