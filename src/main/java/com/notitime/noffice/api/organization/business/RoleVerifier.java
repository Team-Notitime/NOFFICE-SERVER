package com.notitime.noffice.api.organization.business;

import static com.notitime.noffice.domain.JoinStatus.ACTIVE;
import static com.notitime.noffice.domain.OrganizationRole.LEADER;
import static com.notitime.noffice.global.web.BusinessErrorCode.FORBIDDEN_ORGANIZATION_ACCESS;
import static com.notitime.noffice.global.web.BusinessErrorCode.FORBIDDEN_ROLE_ACCESS;
import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_MEMBER;
import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_ORGANIZATION;

import com.notitime.noffice.domain.OrganizationRole;
import com.notitime.noffice.domain.organization.persistence.OrganizationMemberRepository;
import com.notitime.noffice.global.exception.ForbiddenException;
import com.notitime.noffice.global.exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleVerifier {

	private final OrganizationMemberRepository organizationMemberRepository;

	public void verifyJoinedMember(Long memberId, Long organizationId) {
		if (!isActiveMember(memberId, organizationId)) {
			throw new ForbiddenException(FORBIDDEN_ORGANIZATION_ACCESS);
		}
	}

	public boolean isMemberInOrganization(Long memberId, Long organizationId) {
		return organizationMemberRepository.existsByMemberIdAndOrganizationId(memberId, organizationId);
	}

	public OrganizationRole findRole(Long memberId, Long organizationId) {
		return organizationMemberRepository.findByOrganizationIdAndMemberId(organizationId, memberId)
				.orElseThrow(() -> new NotFoundException("멤버가 속한 조직이 없습니다.", NOT_FOUND_ORGANIZATION))
				.getRole();
	}

	public void verifyLeader(Long memberId, Long organizationId) {
		if (!isActiveMemberWithRole(memberId, organizationId, LEADER)) {
			throw new ForbiddenException(FORBIDDEN_ROLE_ACCESS);
		}
	}

	private void verifyRole(Long memberId, Long organizationId, OrganizationRole role) {
		if (isActiveMemberWithRole(memberId, organizationId, role)) {
			throw new ForbiddenException(FORBIDDEN_ORGANIZATION_ACCESS);
		}
	}

	private boolean isActiveMemberWithRole(Long memberId, Long organizationId, OrganizationRole role) {
		return organizationMemberRepository.existsByMemberIdAndOrganizationIdAndRoleAndStatus(memberId, organizationId,
				role, ACTIVE);
	}

	private boolean isActiveMember(Long memberId, Long organizationId) {
		return organizationMemberRepository.existsByMemberIdAndOrganizationIdAndStatus(memberId, organizationId,
				ACTIVE);
	}

	public void verifyMultipleMembers(Long organizationId, List<Long> memberIds) {
		List<Long> activeMemberIds = organizationMemberRepository.findPendingMembers(organizationId, memberIds);
		if (activeMemberIds.size() != memberIds.size()) {
			List<Long> invalidMemberIds = new ArrayList<>(memberIds);
			invalidMemberIds.removeAll(activeMemberIds);
			throw new NotFoundException("잘못된 멤버 식별자: " + invalidMemberIds, NOT_FOUND_MEMBER);
		}
	}
}