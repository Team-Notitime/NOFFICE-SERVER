package com.notitime.noffice.domain.notification.persistence;

import com.notitime.noffice.domain.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
