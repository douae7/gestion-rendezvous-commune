package com.example.backend.controller;

import com.example.backend.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/api/reports")
    public class ReportController  {

        private final ReportService service;

        public ReportController(ReportService service) {
            this.service = service;
        }



        @GetMapping("/services")
        public Map<String, Integer> services() {
            return service.byService();
        }

        @GetMapping("/status")
        public Map<String, Integer> status() {
            return service.byStatus();
        }

        @GetMapping("/months")
        public Map<String, Integer> months() {
            return service.byMonth();
        }
    }

