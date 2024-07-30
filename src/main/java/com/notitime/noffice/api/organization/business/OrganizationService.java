package com.notitime.noffice.api.organization.business;

import com.notitime.noffice.domain.organization.persistence.OrganizationMemberRepository;
import com.notitime.noffice.response.OrganizationResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrganizationService {

	private final OrganizationMemberRepository organizationMemberRepository;

	public OrganizationResponses getOrganizationsByMemberId(Long memberId) {
		return OrganizationResponses.from(organizationMemberRepository.findByMemberId(memberId));
	}
}
