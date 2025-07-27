package com.example.promise.domain.notification.controller;

import com.example.promise.domain.notification.service.NotificationService;
import com.example.promise.global.auth.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.promise.domain.notification.entity.Notification;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<Notification> getMyNotifications(@AuthUser Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @PostMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }
}
