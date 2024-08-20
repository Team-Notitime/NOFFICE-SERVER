package com.notitime.noffice.domain.announcement.persistence;

import com.notitime.noffice.domain.announcement.model.AnnouncementReadStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnnouncementReadStatusRepository extends JpaRepository<AnnouncementReadStatus, Long> {
	Long countByAnnouncementId(Long announcementId);

	boolean existsByMemberIdAndAnnouncementId(Long memberId, Long announcementId);

	@Query("SELECT ars.member.id FROM AnnouncementReadStatus ars WHERE ars.announcement.id = :announcementId AND ars.isRead = false")
	List<Long> findUnreadMemberIds(Long announcementId);
}
