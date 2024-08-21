package com.notitime.noffice.domain.announcement.persistence;

import com.notitime.noffice.domain.announcement.model.AnnouncementReadStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnnouncementReadStatusRepository extends JpaRepository<AnnouncementReadStatus, Long> {
	Long countByAnnouncementId(Long announcementId);

	boolean existsByMemberIdAndAnnouncementId(Long memberId, Long announcementId);

	@Query("SELECT ars.member.id FROM AnnouncementReadStatus ars WHERE ars.announcement.id = :announcementId AND ars.isRead = false")
	List<Long> findUnreadMemberIds(Long announcementId);

	@Query("SELECT ars.member FROM AnnouncementReadStatus ars WHERE ars.announcement.id = :announcementId AND ars.isRead = true")
	List<AnnouncementReadStatus> findReadMembers(@Param("announcementId") Long announcementId);

	@Query("SELECT ars.member FROM AnnouncementReadStatus ars WHERE ars.announcement.id = :announcementId AND ars.isRead = false")
	List<AnnouncementReadStatus> findUnReadMembers(@Param("announcementId") Long announcementId);

	List<AnnouncementReadStatus> findByAnnouncementId(Long announcementId);

	/**
	 * 특정 공지에 대한 열람 상태를 조회합니다.
	 *
	 * @param announcementId 공지 ID
	 * @param memberId       회원 ID
	 * @return 열람 상태
	 */
	AnnouncementReadStatus findByAnnouncementIdAndMemberId(Long announcementId, Long memberId);
}