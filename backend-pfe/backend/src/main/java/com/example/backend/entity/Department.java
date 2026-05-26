package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "head_agent_id")
    @JsonIgnoreProperties({"departement", "appointments"})
    private User headAgent;

    private String phone;

    private boolean active = true;

    // 📄 PDF document (path)
    private String documentUrl;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<User> agents;

}