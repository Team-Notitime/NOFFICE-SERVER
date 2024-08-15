package com.notitime.noffice.domain.announcement.persistence;

import com.notitime.noffice.domain.announcement.model.AnnouncementReadStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementReadStatusRepository extends JpaRepository<AnnouncementReadStatus, Long> {
}
