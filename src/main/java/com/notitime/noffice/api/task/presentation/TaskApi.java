package com.notitime.noffice.api.task.presentation;

import com.notitime.noffice.auth.LoginUser;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.TaskBulkCreateRequest;
import com.notitime.noffice.request.TaskCreateRequest;
import com.notitime.noffice.response.AssignedTaskResponse;
import com.notitime.noffice.response.TaskCreateResponse;
import com.notitime.noffice.response.TaskCreateResponses;
import com.notitime.noffice.response.TaskResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "투두", description = "노티 하위 발급 투두 리스트 관련 API")
interface TaskApi {
	@Operation(summary = "투두 목록 조회", responses = {
			@ApiResponse(responseCode = "200", description = "투두 목록 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "등록된 투두가 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<TaskResponses> getTasks();

	@Operation(summary = "투두 생성", responses = {
			@ApiResponse(responseCode = "201", description = "투두 생성 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "400", description = "투두 생성 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<TaskCreateResponse> createTask(TaskCreateRequest taskCreateRequest);

	@Operation(summary = "투두 대량 생성", responses = {
			@ApiResponse(responseCode = "201", description = "투두 대량 생성 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "400", description = "투두 대량 생성 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<TaskCreateResponses> createBulkTask(TaskBulkCreateRequest taskBulkCreateRequest);

	@Operation(summary = "투두 삭제", responses = {
			@ApiResponse(responseCode = "204", description = "투두 삭제 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "400", description = "투두 삭제 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> deleteTask(@PathVariable Long taskId);

	@Operation(summary = "사용자 할당 투두 목록 조회", responses = {
			@ApiResponse(responseCode = "200", description = "사용자 할당 투두 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "사용자 할당된 투두가 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Slice<AssignedTaskResponse>> getAssignedTasks(@LoginUser Long memberId, Pageable pageable);
}
