package com.notitime.noffice.api.notification.business;

import static com.notitime.noffice.global.response.BusinessErrorCode.NOT_FOUND_MEMBER;

import com.notitime.noffice.domain.FcmToken;
import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementRepository;
import com.notitime.noffice.domain.fcmtoken.persistence.FcmTokenRepository;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.domain.notification.model.Notification;
import com.notitime.noffice.domain.notification.persistence.NotificationRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.request.AnnouncementCreateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final AnnouncementRepository announcementRepository;
	private final FcmTokenRepository fcmTokenRepository;
	private final MemberRepository memberRepository;

	public void create(AnnouncementCreateRequest request, Announcement announcement) {
		List<Notification> notifications = NotificationAssembler.assemble(request, announcement);
		notificationRepository.saveAll(notifications);
		notifications.forEach(announcement::addNotification);
	}

	public void saveFcmToken(Long memberId, String fcmToken) {
		Member member = memberRepository.findById(memberId).orElseThrow(
				() -> new NotFoundException(NOT_FOUND_MEMBER)
		);
		FcmToken token = fcmTokenRepository.save(FcmToken.of(member, fcmToken));
		member.addFcmToken(token);
	}
}
