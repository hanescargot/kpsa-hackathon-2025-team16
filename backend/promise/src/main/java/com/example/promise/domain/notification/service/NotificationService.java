package com.example.promise.domain.notification.service;

import com.example.promise.domain.notification.entity.Notification;
import com.example.promise.domain.notification.repository.NotificationRepository;
import com.example.promise.domain.user.entity.NormalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(NormalUser user, String content) {
        Notification notification = Notification.builder()
                .user(user)
                .content(content)
                .build();
        notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public void markAsRead(Long notificationId) {
        Notification noti = notificationRepository.findById(notificationId).orElseThrow();
        noti.setRead(true);
    }
}
