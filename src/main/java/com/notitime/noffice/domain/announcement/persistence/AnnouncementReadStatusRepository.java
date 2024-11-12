package com.notitime.noffice.domain.announcement.persistence;

import com.notitime.noffice.domain.announcement.model.AnnouncementReadStatus;
import com.notitime.noffice.domain.member.model.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnnouncementReadStatusRepository extends JpaRepository<AnnouncementReadStatus, Long> {
	Long countByAnnouncementId(Long announcementId);

	boolean existsByMemberIdAndAnnouncementId(Long memberId, Long announcementId);

	@Query("SELECT ars.member FROM AnnouncementReadStatus ars WHERE ars.announcement.id = :announcementId AND ars.isRead = true")
	List<Member> findReadMembers(@Param("announcementId") Long announcementId);

	@Query("SELECT ars.member FROM AnnouncementReadStatus ars WHERE ars.announcement.id = :announcementId AND ars.isRead = false")
	List<Member> findUnReadMembers(@Param("announcementId") Long announcementId);
}