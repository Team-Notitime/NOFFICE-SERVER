package com.notitime.noffice.api.organization.business;

import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.persistence.OrganizationMemberRepository;
import com.notitime.noffice.domain.organization.persistence.OrganizationRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.global.response.BusinessErrorCode;
import com.notitime.noffice.response.OrganizationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

	public Slice<OrganizationResponse> getOrganizationsByMemberId(Long memberId, Pageable pageable) {
		Slice<Organization> organizations = organizationMemberRepository.findOrganizationsByMemberId(memberId,
				pageable);
		List<OrganizationResponse> responses = organizations.stream()
				.map(OrganizationResponse::of)
				.toList();
		return new PageImpl<>(responses, pageable, responses.size());
	}

	public Organization getOrganizationEntity(Long organizationId) {
		return organizationRepository.findById(organizationId)
				.orElseThrow(() -> new NotFoundException(BusinessErrorCode.NOT_FOUND));
	}
}
