package com.notitime.noffice.api.member.business;

import com.notitime.noffice.api.organization.business.OrganizationService;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.global.response.BusinessErrorCode;
import com.notitime.noffice.response.MemberResponse;
import com.notitime.noffice.response.OrganizationResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final OrganizationService organizationService;

	public MemberResponse getMember(Long memberId) {
		return MemberResponse.of(getMemberEntity(memberId));
	}

	public OrganizationResponses getJoinedOrganizations(Long memberId) {
		return organizationService.getOrganizationsByMemberId(memberId);
	}

	public Member getMemberEntity(Long memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException(BusinessErrorCode.NOT_FOUND));
	}
}
