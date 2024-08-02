package com.notitime.noffice.domain.announcement.persistence;

import com.notitime.noffice.domain.announcement.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
