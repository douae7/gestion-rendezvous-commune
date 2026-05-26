package com.example.backend.repository;

import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    User save(User user);
    List<User> findByRole(String role);


    List<User> findByDepartement_IdAndRole(Long departementId, String role);

    List<User> findByDepartement_Id(Long departmentId);



    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'CITIZEN'")
    long countCitizens();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'AGENT'")
    long countAgents();


    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.departement.id = :depId")
    void updateStatusByDepartementId(@Param("depId") Long depId,
                                     @Param("status") String status);
}