package com.notitime.noffice.domain.announcement.persistence;

import com.notitime.noffice.domain.announcement.model.AnnouncementImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementImageRepository extends JpaRepository<AnnouncementImage, Long> {
}
