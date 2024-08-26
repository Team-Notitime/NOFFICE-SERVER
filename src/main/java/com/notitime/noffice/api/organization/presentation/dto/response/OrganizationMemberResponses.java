package com.notitime.noffice.api.organization.presentation.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.api.member.presentation.dto.response.MemberInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record OrganizationMemberResponses(
		@Schema(description = "요청자 정보 (LEADER 검증 완료)", requiredMode = REQUIRED)
		MemberInfoResponse requester,
		@Schema(description = "조직의 리더 목록 (LEADER)", requiredMode = REQUIRED)
		List<MemberInfoResponse> leaders,
		@Schema(description = "조직의 멤버 목록 (PARTICIPANT)", requiredMode = NOT_REQUIRED)
		List<MemberInfoResponse> participants
) {
	public static OrganizationMemberResponses of(MemberInfoResponse requester, List<MemberInfoResponse> leaders,
	                                             List<MemberInfoResponse> participants) {
		return new OrganizationMemberResponses(requester, leaders, participants);
	}
}
