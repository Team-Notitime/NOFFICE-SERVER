package com.notitime.noffice.domain.notification.model;

import com.notitime.noffice.domain.announcement.model.Announcement;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "announcement_id", nullable = false)
	private Announcement announcement;

	private LocalDateTime sendAt;

	private Notification(Announcement announcement, LocalDateTime sendAt) {
		this.announcement = announcement;
		this.title = announcement.getTitle();
		this.content = announcement.getContent();
		this.sendAt = sendAt;
	}

	public static Notification createNotification(Announcement announcement, LocalDateTime sendAt) {
		return new Notification(announcement, sendAt);
	}
}
