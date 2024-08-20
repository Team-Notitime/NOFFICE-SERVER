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
@AllArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
public class Announcement extends BaseTimeEntity {

	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

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
	private final List<Notification> notifications = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id")
	private Organization organization;

	@Builder
	public Announcement(String title, String content, LocalDateTime endAt,
	                    Member member, Organization organization,
	                    String profileImageUrl, boolean isFaceToFace,
	                    String placeLinkName, String placeLinkUrl) {
		this.title = title;
		this.content = content;
		this.endAt = endAt;
		this.member = member;
		this.organization = organization;
		this.profileImageUrl = profileImageUrl;
		this.isFaceToFace = isFaceToFace;
		this.placeLinkName = placeLinkName;
		this.placeLinkUrl = placeLinkUrl;
	}

	public static Announcement create(String title, String content, LocalDateTime endAt,
	                                  Member member, Organization organization,
	                                  String profileImageUrl, Boolean isFaceToFace,
	                                  String placeLinkName, String placeLinkUrl) {
		return Announcement.builder()
				.title(title)
				.content(content)
				.endAt(endAt)
				.member(member)
				.organization(organization)
				.profileImageUrl(profileImageUrl)
				.isFaceToFace(isFaceToFace)
				.placeLinkName(placeLinkName)
				.placeLinkUrl(placeLinkUrl)
				.build();
	}

	public void withTasks(List<TaskCreateRequest> taskRequests) {
		if (taskRequests != null) {
			this.tasks = taskRequests.stream()
					.map(taskRequest -> Task.create(taskRequest.content(), this))
					.toList();
		}
	}

	public void addNotification(Notification notification) {
		this.notifications.add(notification);
	}
}
