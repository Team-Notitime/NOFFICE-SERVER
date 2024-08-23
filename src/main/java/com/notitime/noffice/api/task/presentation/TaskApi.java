package com.notitime.noffice.api.task.presentation;

import com.notitime.noffice.api.task.presentation.dto.request.TaskModifyRequest;
import com.notitime.noffice.api.task.presentation.dto.response.AssignedTaskResponse;
import com.notitime.noffice.api.task.presentation.dto.response.TaskModifyResponse;
import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.web.NofficeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;

@Tag(name = "투두", description = "노티 하위 발급 투두 리스트 관련 API")
interface TaskApi {

	@Operation(summary = "투두 수정", responses = {
			@ApiResponse(responseCode = "200", description = "투두 수정 성공"),
			@ApiResponse(responseCode = "400", description = "투두 수정 실패", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<TaskModifyResponse> modify(TaskModifyRequest taskModifyRequest);

	@Operation(summary = "[인증] 사용자 할당 투두 목록 조회", responses = {
			@ApiResponse(responseCode = "200", description = "사용자 할당 투두 조회 성공"),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "사용자 할당된 투두가 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Slice<AssignedTaskResponse>> getAssigned(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                                         @PageableDefault(size = 5)
	                                                         @SortDefault(sort = "id", direction = Sort.Direction.DESC)
	                                                         Pageable pageable);
}
