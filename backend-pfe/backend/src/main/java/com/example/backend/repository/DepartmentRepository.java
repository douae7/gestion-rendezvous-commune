package com.example.backend.repository;

import com.example.backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByNameIgnoreCase(String name);
    Optional<Department> findFirstByNameContainingIgnoreCase(String name);
    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.departement.id = :depId")
    void updateStatusByDepartementId(@Param("depId") Long depId,
                                     @Param("status") String status);

}