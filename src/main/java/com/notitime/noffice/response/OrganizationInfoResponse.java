package com.notitime.noffice.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.domain.organization.model.Organization;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record OrganizationInfoResponse(
		@Schema(requiredMode = REQUIRED, description = "조직 ID", example = "1")
		Long organizationId,
		@Schema(requiredMode = REQUIRED, description = "조직 이름", example = "CMC 15th : NotiTime")
		String organizationName,
		@Schema(requiredMode = REQUIRED, description = "프로필 이미지", example = "https://notitime.com/profile.png")
		String profileImage,
		@Schema(requiredMode = REQUIRED, description = "카테고리 리스트", example = "['컴퓨터공학', '전자공학']")
		List<String> categories,
		@Schema(requiredMode = NOT_REQUIRED, description = "관리자 사용자 수")
		Long leaderCount,
		@Schema(requiredMode = NOT_REQUIRED, description = "일반 사용자 수")
		Long participantCount,
		@Schema(requiredMode = NOT_REQUIRED, description = "가입 대기자 여부", example = "true")
		Boolean isPending) {
	public static OrganizationInfoResponse of(Organization organization, List<String> categories,
	                                          Long leaderCount, Long participantCount, Boolean isPending) {
		return new OrganizationInfoResponse(
				organization.getId(), organization.getName(), organization.getProfileImage(),
				categories, leaderCount, participantCount, isPending);
	}
}
