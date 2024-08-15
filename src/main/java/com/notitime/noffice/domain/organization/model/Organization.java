package com.notitime.noffice.domain.organization.model;

import com.notitime.noffice.domain.BaseTimeEntity;
import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.category.model.Category;
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

	public Organization addCategories(List<Category> categories) {
		categories.forEach(category -> this.categories.add(new OrganizationCategory(this, category)));
		return this;
	}

	public Organization updateCategories(List<Category> categories) {
		for (Category category : categories) {
			if (this.categories.stream().noneMatch(oc -> oc.getCategory().equals(category))) {
				this.categories.add(new OrganizationCategory(this, category));
			}
		}
		this.categories.removeIf(oc -> categories.stream().noneMatch(category -> oc.getCategory().equals(category)));
		return this;
	}
}
