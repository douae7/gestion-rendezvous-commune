package com.example.backend.repository;

import com.example.backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // ✅ Notifications user
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    // ✅ Notifications user ou role
    List<Notification> findByUserIdOrRoleOrderByCreatedAtDesc(Long userId, String role);

    // ✅ Marquer toutes notifications user comme lues
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.user.id = :userId")
    void markAllAsReadByUser(@Param("userId") Long userId);

    // ✅ Marquer toutes notifications role comme lues
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.role = :role")
    void markAllAsReadByRole(@Param("role") String role);

    // ✅ Supprimer notifications user
    void deleteByUserId(Long userId);
}