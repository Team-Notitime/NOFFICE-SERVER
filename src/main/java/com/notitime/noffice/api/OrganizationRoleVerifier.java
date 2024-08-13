package com.notitime.noffice.api;

import com.notitime.noffice.domain.JoinStatus;
import com.notitime.noffice.domain.OrganizationRole;
import com.notitime.noffice.domain.organization.persistence.OrganizationMemberRepository;
import com.notitime.noffice.global.exception.ForbiddenException;
import com.notitime.noffice.global.response.BusinessErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationRoleVerifier {

	private final OrganizationMemberRepository organizationMemberRepository;

	public void verifyJoinedMember(Long memberId, Long organizationId) {
		if (isActiveMember(memberId, organizationId)) {
			throw new ForbiddenException(BusinessErrorCode.FORBIDDEN_ORGANIZATION_ACCESS);
		}
	}

	public void verifyRole(Long memberId, Long organizationId, OrganizationRole role) {
		if (isActiveMemberWithRole(memberId, organizationId, role)) {
			throw new ForbiddenException(BusinessErrorCode.FORBIDDEN_ORGANIZATION_ACCESS);
		}
	}

	private boolean isActiveMemberWithRole(Long memberId, Long organizationId, OrganizationRole role) {
		return organizationMemberRepository.existsByMemberIdAndOrganizationIdAndRoleAndStatus(memberId, organizationId,
				role, JoinStatus.ACTIVE);
	}

	private boolean isActiveMember(Long memberId, Long organizationId) {
		return organizationMemberRepository.existsByMemberIdAndOrganizationIdAndStatus(memberId, organizationId,
				JoinStatus.ACTIVE);
	}
}