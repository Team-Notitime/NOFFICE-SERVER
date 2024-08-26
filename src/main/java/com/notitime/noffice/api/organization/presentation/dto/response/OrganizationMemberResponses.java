package com.notitime.noffice.api.organization.presentation.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.api.member.presentation.dto.response.MemberInfoResponse;
import com.notitime.noffice.domain.member.model.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record OrganizationMemberResponses(
		@Schema(description = "조직의 리더 목록 (LEADER)", requiredMode = REQUIRED)
		List<MemberInfoResponse> leaders,
		@Schema(description = "조직의 멤버 목록 (PARTICIPANT)", requiredMode = REQUIRED)
		List<MemberInfoResponse> participants
) {
	public static OrganizationMemberResponses of(List<Member> leaders, List<Member> participants) {
		return new OrganizationMemberResponses(
				leaders.stream().map(MemberInfoResponse::from).toList(),
				participants.stream().map(MemberInfoResponse::from).toList()
		);
	}
}
