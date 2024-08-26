package com.notitime.noffice.api.organization.business;

import static com.notitime.noffice.domain.JoinStatus.ACTIVE;
import static com.notitime.noffice.domain.OrganizationRole.LEADER;
import static com.notitime.noffice.global.web.BusinessErrorCode.FORBIDDEN_ORGANIZATION_ACCESS;
import static com.notitime.noffice.global.web.BusinessErrorCode.FORBIDDEN_ROLE_ACCESS;
import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_ORGANIZATION;

import com.notitime.noffice.domain.OrganizationRole;
import com.notitime.noffice.domain.organization.persistence.OrganizationMemberRepository;
import com.notitime.noffice.global.exception.ForbiddenException;
import com.notitime.noffice.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleVerifier {

	private final OrganizationMemberRepository organizationMemberRepository;

	public void verifyJoinedMember(Long memberId, Long organizationId) {
		if (!isActiveMember(memberId, organizationId)) {
			throw new ForbiddenException(FORBIDDEN_ORGANIZATION_ACCESS);
		}
	}

	public OrganizationRole findRole(Long memberId, Long organizationId) {
		return organizationMemberRepository.findByOrganizationIdAndMemberId(organizationId, memberId)
				.orElseThrow(() -> new NotFoundException("멤버가 속한 조직이 없습니다.", NOT_FOUND_ORGANIZATION))
				.getRole();
	}

	public void verifyLeader(Long memberId, Long organizationId) {
		if (!organizationMemberRepository.existsByMemberIdAndOrganizationIdAndRole(memberId,
				organizationId, LEADER)) {
			throw new ForbiddenException(FORBIDDEN_ROLE_ACCESS);
		}
	}

	private boolean isActiveMember(Long memberId, Long organizationId) {
		return organizationMemberRepository.existsByMemberIdAndOrganizationIdAndStatus(memberId, organizationId,
				ACTIVE);
	}
}