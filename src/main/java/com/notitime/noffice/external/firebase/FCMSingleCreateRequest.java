package com.notitime.noffice.external.firebase;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;

public record FCMSingleCreateRequest(
		@Schema(requiredMode = REQUIRED, description = "알림 보내려는 조직 ID", example = "1")
		Long organizationId,
		@Schema(requiredMode = REQUIRED, description = "수신자의 Member 식별자 (반드시 조직원이어야 합니다.)", example = "1")
		List<Long> targetMemberIds,
		@Schema(requiredMode = RequiredMode.REQUIRED, description = "알림 제목", example = "알림 제목")
		String notificationTitle,
		@Schema(requiredMode = RequiredMode.REQUIRED, description = "알림 내용", example = "알림 내용")
		String notificationBody
) {
	public static FCMSingleCreateRequest of(Long organizationId, List<Long> targetMemberIds, String title,
	                                        String body) {
		return new FCMSingleCreateRequest(organizationId, targetMemberIds, title, body);
	}
}
