package com.example.backend.dto;

public class ServiceCountDTO {

    private String service;
    private Long count;

    public ServiceCountDTO(String service, Long count) {
        this.service = service;
        this.count = count;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}