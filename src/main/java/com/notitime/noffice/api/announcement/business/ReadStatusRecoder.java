package com.notitime.noffice.api.announcement.business;

import com.notitime.noffice.api.organization.business.RoleVerifier;
import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.announcement.model.AnnouncementReadStatus;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementReadStatusRepository;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementRepository;
import com.notitime.noffice.domain.member.model.Member;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReadStatusRecoder {

	private final AnnouncementReadStatusRepository announcementReadStatusRepository;
	private final AnnouncementRepository announcementRepository;
	private final RoleVerifier roleVerifier;

	public void record(Member member, Announcement announcement) {
		announcementReadStatusRepository.save(AnnouncementReadStatus.builder()
				.readAt(LocalDateTime.now())
				.isRead(true)
				.member(member)
				.announcement(announcement)
				.build());
	}

	public List<Member> findReadMembers(Long announcementId) {
		return announcementReadStatusRepository.findReadMembers(announcementId);
	}

	public List<Member> findUnReadMembers(Long announcementId) {
		return announcementReadStatusRepository.findUnReadMembers(announcementId);
	}

	public Long countReader(Long announcementId) {
		return announcementReadStatusRepository.countByAnnouncementId(announcementId);
	}

	public List<Long> getUnreadMemberIds(Long announcementId) {
		return announcementReadStatusRepository.findUnreadMemberIds(announcementId);
	}

	public boolean isRead(Long memberId, Long announcementId) {
		return announcementReadStatusRepository.existsByMemberIdAndAnnouncementId(memberId, announcementId);
	}
}
