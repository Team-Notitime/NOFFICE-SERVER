package com.notitime.noffice.domain.announcement.model;

import com.notitime.noffice.domain.BaseTimeEntity;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.notification.model.Notification;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.task.model.Task;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Announcement extends BaseTimeEntity {

	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(columnDefinition = "TEXT")
	private String profileImageUrl;

	@Column(nullable = false)
	private boolean isFaceToFace;

	private String placeLinkName;

	@Column(columnDefinition = "TEXT")
	private String placeLinkUrl;

	private LocalDateTime endAt;

	@OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> tasks = new ArrayList<>();

	@OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Notification> notifications = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id")
	private Organization organization;

	public static Announcement createAnnouncement(String title, String content, LocalDateTime endAt, Member member,
	                                              Organization organizaion) {
		return new Announcement(null, title, content, null, false, null, null, endAt,
				null, new ArrayList<>(), member, organizaion);
	}

	public void withTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void withProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public void withIsFaceToFace(Boolean isFaceToFace) {
		this.isFaceToFace = isFaceToFace != null ? isFaceToFace : this.isFaceToFace;
	}

	public void withPlaceLinkName(String placeLinkName) {
		this.placeLinkName = placeLinkName;
	}

	public void withPlaceLinkUrl(String placeLinkUrl) {
		this.placeLinkUrl = placeLinkUrl;
	}
}
