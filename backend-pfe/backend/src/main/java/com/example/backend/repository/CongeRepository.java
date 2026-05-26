package com.example.backend.repository;

import com.example.backend.entity.Conge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CongeRepository extends JpaRepository<Conge, Long> {


    @Query("""
        SELECT COUNT(c) > 0 FROM Conge c
        WHERE c.agent.id = :agentId
        AND :date BETWEEN c.startDate AND c.endDate
    """)
    boolean isAgentOnLeave(Long agentId, LocalDate date);


    List<Conge> findByAgentId(Long agentId);
}
