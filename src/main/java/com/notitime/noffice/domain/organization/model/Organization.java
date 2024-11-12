package com.notitime.noffice.domain.organization.model;

import com.notitime.noffice.domain.BaseTimeEntity;
import com.notitime.noffice.domain.JoinStatus;
import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.category.model.Category;
import com.notitime.noffice.domain.member.model.Member;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Organization extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private LocalDateTime endAt;

	@Column(columnDefinition = "TEXT")
	private String profileImage;

	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Announcement> announcements = new ArrayList<>();

	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<OrganizationCategory> categories = new ArrayList<>();

	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<OrganizationMember> members = new ArrayList<>();

	public static Organization create(String name, LocalDateTime endAt, String profileImage, List<Category> categories,
	                                  Member leader) {
		return new Organization(null, name, endAt, profileImage, categories, leader);
	}

	private Organization(Long id, String name, LocalDateTime endAt, String profileImage,
	                     List<Category> categories, Member creator) {
		this.id = id;
		this.name = name;
		this.endAt = endAt;
		this.profileImage = profileImage;
		addCategories(categories);
		addLeader(creator);
	}

	public void updateCategories(List<Category> categories) {
		for (Category category : categories) {
			if (this.categories.stream().noneMatch(oc -> oc.getCategory().equals(category))) {
				this.categories.add(new OrganizationCategory(this, category));
			}
		}
		this.categories.removeIf(oc -> categories.stream().noneMatch(category -> oc.getCategory().equals(category)));
	}

	private void addCategories(List<Category> categories) {
		categories.forEach(category -> this.categories.add(new OrganizationCategory(this, category)));
	}

	public void addLeader(Member leader) {
		this.members.add(OrganizationMember.joinAsLeader(leader.getName(), this, leader));
	}

	public void addAnnouncement(Announcement announcement) {
		if (!this.announcements.contains(announcement)) {
			announcements.add(announcement);
		}
		announcement.setOrganization(this);
	}

	public List<Member> getMembersByStatus(JoinStatus joinStatus) {
		return this.members.stream()
				.filter(organizationMember -> organizationMember.getStatus() == joinStatus)
				.map(OrganizationMember::getMember)
				.toList();
	}

	public void deleteProfileImage() {
		this.profileImage = null;
	}

	public void updateProfileImage(String imageUrl) {
		this.profileImage = imageUrl;
	}
}
