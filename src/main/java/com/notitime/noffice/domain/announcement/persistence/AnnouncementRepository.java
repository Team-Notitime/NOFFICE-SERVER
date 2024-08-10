package com.notitime.noffice.domain.announcement.persistence;

import com.notitime.noffice.domain.announcement.model.Announcement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

	Slice<Announcement> findByOrganizationId(Long organizationId, Pageable pageable);
}
