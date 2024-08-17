package com.notitime.noffice.api.task.presentation;

import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.TaskModifyRequest;
import com.notitime.noffice.response.AssignedTaskResponse;
import com.notitime.noffice.response.TaskModifyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@Tag(name = "투두", description = "노티 하위 발급 투두 리스트 관련 API")
interface TaskApi {

	@Operation(summary = "투두 수정", responses = {
			@ApiResponse(responseCode = "200", description = "투두 수정 성공"),
			@ApiResponse(responseCode = "400", description = "투두 수정 실패")
	})
	NofficeResponse<TaskModifyResponse> modify(TaskModifyRequest taskModifyRequest);

	@Operation(summary = "사용자 할당 투두 목록 조회", responses = {
			@ApiResponse(responseCode = "200", description = "사용자 할당 투두 조회 성공"),
			@ApiResponse(responseCode = "404", description = "사용자 할당된 투두가 없습니다.")
	})
	NofficeResponse<Slice<AssignedTaskResponse>> getAssigned(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                                         Pageable pageable);
}
