package com.notitime.noffice.domain.organization.model;

import com.notitime.noffice.domain.category.model.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
		name = "organization_category",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"organization_id", "category_id"})
		}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrganizationCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "organization_id")
	private Organization organization;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	public OrganizationCategory(Organization organization, Category category) {
		this.organization = organization;
		this.category = category;
	}
}
