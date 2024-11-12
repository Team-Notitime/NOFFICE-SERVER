package com.notitime.noffice.api.announcement.business;

import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_ANNOUNCEMENT;
import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_MEMBER;

import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.announcement.model.AnnouncementReadStatus;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementReadStatusRepository;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementRepository;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReadStatusRecoder {

	private final AnnouncementReadStatusRepository announcementReadStatusRepository;
	private final AnnouncementRepository announcementRepository;
	private final MemberRepository memberRepository;

	public void recordMemberRead(Long memberId, Long announcementId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));
		Announcement announcement = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_ANNOUNCEMENT));
		if (!isAlreadyRead(memberId, announcementId)) {
			announcementReadStatusRepository.save(AnnouncementReadStatus.record(member, announcement));
		}
	}

	private Boolean isAlreadyRead(Long memberId, Long announcementId) {
		return announcementReadStatusRepository.existsByMemberIdAndAnnouncementId(memberId, announcementId);
	}
}
