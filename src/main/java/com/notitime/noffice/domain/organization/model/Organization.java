package com.notitime.noffice.domain.organization.model;

import com.notitime.noffice.domain.BaseTimeEntity;
import com.notitime.noffice.domain.announcement.model.Announcement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Organization extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private LocalDateTime endAt;

	@Column(columnDefinition = "TEXT")
	private String profileImage;

	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Announcement> announcements = new ArrayList<>();

	@OneToMany(mappedBy = "organization")
	private List<OrganizationCategory> categories = new ArrayList<>();

	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrganizationMember> members = new ArrayList<>();
}
