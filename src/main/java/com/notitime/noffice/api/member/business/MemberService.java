package com.notitime.noffice.api.member.business;

import com.notitime.noffice.api.member.presentation.dto.request.MemberAliasUpdateRequest;
import com.notitime.noffice.api.member.presentation.dto.request.MemberProfileUpdateRequest;
import com.notitime.noffice.api.member.presentation.dto.response.MemberResponse;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.global.web.BusinessErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberResponse getMember(Long memberId) {
		return MemberResponse.of(getMemberEntity(memberId));
	}

	private Member getMemberEntity(Long memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException(BusinessErrorCode.NOT_FOUND_MEMBER));
	}

	public void deleteProfileImage(Long memberId) {
		Member member = getMemberEntity(memberId);
		member.deleteProfileImage();
		memberRepository.save(member);
	}

	public void updateAlias(Long memberId, MemberAliasUpdateRequest request) {
		Member member = getMemberEntity(memberId);
		member.updateAlias(request.alias());
		memberRepository.save(member);
	}

	public void updateProfileImage(Long memberId, MemberProfileUpdateRequest request) {
		Member member = getMemberEntity(memberId);
		member.updateProfileImage(request.imageUrl());
		memberRepository.save(member);
	}
}
