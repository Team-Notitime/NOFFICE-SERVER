package com.notitime.noffice.domain.announcement.model;

import com.notitime.noffice.domain.BaseTimeEntity;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.notification.model.Notification;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.task.model.Task;
import com.notitime.noffice.request.AnnouncementCreateRequest;
import com.notitime.noffice.request.AnnouncementUpdateRequest;
import com.notitime.noffice.request.TaskCreateRequest;
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
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@DynamicInsert
@DynamicUpdate
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

	public static Announcement create(AnnouncementCreateRequest request, Member member,
	                                  Organization organization) {
		Announcement announcement = Announcement.builder()
				.title(request.title())
				.content(request.content())
				.endAt(LocalDateTime.parse(request.endAt(), DATE_TIME_FORMATTER))
				.member(member)
				.organization(organization)
				.build();
		announcement.withProfileImageUrl(request.profileImageUrl());
		announcement.withIsFaceToFace(request.isFaceToFace());
		announcement.withPlaceLinkName(request.placeLinkName());
		announcement.withPlaceLinkUrl(request.placeLinkUrl());
		announcement.withTasks(request.tasks());
		return announcement;
	}

	public void update(AnnouncementUpdateRequest request) {
		this.title = request.title();
		this.content = request.content();
		this.endAt = LocalDateTime.parse(request.endAt(), DATE_TIME_FORMATTER);
		this.profileImageUrl = request.profileImageUrl();
		this.isFaceToFace = request.isFaceToFace();
		Optional.ofNullable(request.tasks()).ifPresent(this::withTasks);
		Optional.ofNullable(request.profileImageUrl()).ifPresent(this::withProfileImageUrl);
		Optional.ofNullable(request.isFaceToFace()).ifPresent(this::withIsFaceToFace);
		Optional.ofNullable(request.placeLinkName()).ifPresent(this::withPlaceLinkName);
		Optional.ofNullable(request.placeLinkUrl()).ifPresent(this::withPlaceLinkUrl);
	}

	public void withTasks(List<TaskCreateRequest> request) {
		this.tasks = request.stream()
				.map(task -> Task.create(task.content(), this)).toList();
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
