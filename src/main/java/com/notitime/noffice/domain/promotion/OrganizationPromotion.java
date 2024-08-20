package com.notitime.noffice.domain.promotion;

import com.notitime.noffice.domain.organization.model.Organization;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrganizationPromotion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "organization_id", nullable = false)
	private Organization organization;

	@ManyToOne
	@JoinColumn(name = "promotion_id", nullable = false)
	private Promotion promotion;

	private LocalDateTime endAt;

	public static OrganizationPromotion of(Organization organization, Promotion promotion, LocalDateTime endAt) {
		return new OrganizationPromotion(null, organization, promotion, endAt);
	}
}
