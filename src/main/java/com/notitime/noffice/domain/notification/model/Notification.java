package com.notitime.noffice.domain.notification.model;

import com.notitime.noffice.domain.announcement.model.Announcement;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private NoticeType noticeType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "announcement_id", nullable = false)
	private Announcement announcement;

	private LocalDateTime sendAt;

	private Notification(Announcement announcement, LocalDateTime sendAt, NoticeType noticeType) {
		this.announcement = announcement;
		this.sendAt = sendAt;
		this.noticeType = noticeType;
	}

	public static Notification createNotification(Announcement announcement, LocalDateTime sendAt,
	                                              NoticeType noticeType) {
		return new Notification(announcement, sendAt, noticeType);
	}
}
