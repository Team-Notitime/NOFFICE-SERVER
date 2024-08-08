package com.notitime.noffice.api.organization.business;

import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.persistence.OrganizationMemberRepository;
import com.notitime.noffice.domain.organization.persistence.OrganizationRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.global.response.BusinessErrorCode;
import com.notitime.noffice.response.OrganizationResponse;
import com.notitime.noffice.response.OrganizationResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrganizationService {

	private final OrganizationMemberRepository organizationMemberRepository;
	private final OrganizationRepository organizationRepository;

	public OrganizationResponse getOrganization(Long organizationId) {
		return OrganizationResponse.of(getOrganizationEntity(organizationId));
	}

	public OrganizationResponses getOrganizationsByMemberId(Long memberId) {
		return OrganizationResponses.from(organizationMemberRepository.findByMemberId(memberId));
	}

	public Organization getOrganizationEntity(Long organizationId) {
		return organizationRepository.findById(organizationId)
				.orElseThrow(() -> new NotFoundException(BusinessErrorCode.NOT_FOUND));
	}
}
