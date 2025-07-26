package com.example.promise.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<com.example.promise.domain.notification.entity.Notification, Long> {
    List<com.example.promise.domain.notification.entity.Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
}
