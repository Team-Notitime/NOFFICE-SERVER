package com.notitime.noffice.api.organization.presentation.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.domain.OrganizationRole;
import com.notitime.noffice.domain.organization.model.Organization;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record OrganizationInfoResponse(

		@Schema(description = "요청한 사용자의 조직 내 권한", example = "LEADER")
		OrganizationRole role,
		@Schema(description = "조직 ID", example = "1", requiredMode = REQUIRED)
		Long organizationId,
		@Schema(description = "조직 이름", example = "CMC 15th : NotiTime", requiredMode = REQUIRED)
		String organizationName,
		@Schema(description = "프로필 이미지", example = "https://notitime.com/profile.png", requiredMode = REQUIRED)
		String profileImage,
		@Schema(description = "카테고리 리스트", example = "['컴퓨터공학', '전자공학']", requiredMode = REQUIRED)
		List<String> categories,
		@Schema(description = "관리자 사용자 수", example = "10", requiredMode = NOT_REQUIRED)
		Long leaderCount,
		@Schema(description = "일반 사용자 수", example = "100", requiredMode = NOT_REQUIRED)
		Long participantCount,
		@Schema(description = "가입 대기자 여부", example = "true", requiredMode = NOT_REQUIRED)
		Boolean isPending) {
	public static OrganizationInfoResponse of(OrganizationRole role, Organization organization, List<String> categories,
	                                          Long leaderCount, Long participantCount, Boolean isPending) {
		return new OrganizationInfoResponse(role,
				organization.getId(), organization.getName(), organization.getProfileImage(),
				categories, leaderCount, participantCount, isPending);
	}
}
