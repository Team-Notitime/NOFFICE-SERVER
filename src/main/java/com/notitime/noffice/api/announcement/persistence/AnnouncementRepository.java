package com.notitime.noffice.api.announcement.persistence;

import com.notitime.noffice.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
