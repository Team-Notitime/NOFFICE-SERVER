package com.notitime.noffice.api.member.business;

import com.notitime.noffice.api.member.presentation.dto.request.MemberNameUpdateRequest;
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
		return MemberResponse.of(findById(memberId));
	}

	public void deleteProfileImage(Long memberId) {
		Member member = findById(memberId);
		member.deleteProfileImage();
		memberRepository.save(member);
	}

	public void updateName(Long memberId, MemberNameUpdateRequest request) {
		Member member = findById(memberId);
		member.updateName(request.name());
		memberRepository.save(member);
	}
	
	public void updateProfileImage(Long memberId, MemberProfileUpdateRequest request) {
		Member member = findById(memberId);
		member.updateProfileImage(request.imageUrl());
		memberRepository.save(member);
	}

	public void deleteById(Long memberId) {
		Member member = findById(memberId);
		memberRepository.delete(member);
	}

	private Member findById(Long memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException(BusinessErrorCode.NOT_FOUND_MEMBER));
	}
}
