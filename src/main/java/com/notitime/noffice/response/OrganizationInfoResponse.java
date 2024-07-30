package com.notitime.noffice.response;

import com.notitime.noffice.domain.organization.model.Organization;
import java.time.LocalDateTime;

public record OrganizationInfoResponse(Long id, String name, LocalDateTime endAt, String profileImage) {

	public static OrganizationInfoResponse of(Organization organization) {
		return new OrganizationInfoResponse(
				organization.getId(),
				organization.getName(),
				organization.getEndAt(),
				organization.getProfileImage());
	}
}
