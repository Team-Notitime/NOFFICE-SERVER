package com.notitime.noffice.api.organization.presentation;

import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.response.NofficeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "조직별 권한", description = "조직별 권한 관련 API")
public interface RoleApi {
	@Operation(summary = "조직원 권한 변경", description = "타겟에 해당하는 멤버의 조직 내 권한을 입력한 권한대로 변경합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "권한 변경에 성공하였습니다."),
			@ApiResponse(responseCode = "400", description = "권한 변경에 실패하였습니다."),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다."),
			@ApiResponse(responseCode = "404", description = "조직원이 존재하지 않습니다.")
	})
	NofficeResponse<Void> changeRoles(@AuthMember Long memberId,
	                                  @RequestBody @Valid final ChangeRoleRequest request);
}
