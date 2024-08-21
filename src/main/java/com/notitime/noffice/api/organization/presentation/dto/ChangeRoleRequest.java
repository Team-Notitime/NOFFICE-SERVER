package com.notitime.noffice.api.organization.presentation.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.domain.OrganizationRole;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ChangeRoleRequest(
		@Schema(description = "변경 적용할 권한", requiredMode = REQUIRED, example = "LEADER, PARTICIPANT")
		OrganizationRole role,
		@Schema(description = "변경할 대상 멤버 ID 리스트", requiredMode = REQUIRED, example = "[1, 2, 3]")
		List<Long> memberIds
) {
}
