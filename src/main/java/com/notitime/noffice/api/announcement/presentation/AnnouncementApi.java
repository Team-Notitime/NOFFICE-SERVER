package com.notitime.noffice.api.announcement.presentation;

import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.AnnouncementCreateRequest;
import com.notitime.noffice.request.AnnouncementUpdateRequest;
import com.notitime.noffice.response.AnnouncementResponse;
import com.notitime.noffice.response.AnnouncementResponses;
import com.notitime.noffice.response.TaskResponses;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "노티", description = "노티(조직 내 공지) 관련 API")
public interface AnnouncementApi {

	@Hidden
	@Operation(summary = "미사용 - 사용자에게 할당된 모든 노티 조회", description = "사용자에게 할당된 모든 노티를 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "노티 목록 조회 성공"),
			@ApiResponse(responseCode = "404", description = "등록된 노티가 없습니다.")
	})
	NofficeResponse<AnnouncementResponses> getAnnouncements();

	@Operation(summary = "노티 생성", description = "노티를 생성합니다.", responses = {
			@ApiResponse(responseCode = "201", description = "노티 생성 성공"),
			@ApiResponse(responseCode = "400", description = "노티 생성 실패")
	})
	NofficeResponse<AnnouncementResponse> createAnnouncement(
			@RequestBody final AnnouncementCreateRequest announcementCreateRequest);

	@Operation(summary = "[인증] 조직에 발급된 노티 열람", description = "열람하려는 노티를 조회하고, 열람 기록에 추가합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "노티 단일 조회 성공"),
			@ApiResponse(responseCode = "404", description = "해당 노티가 없습니다.")
	})
	NofficeResponse<AnnouncementResponse> readAnnouncement(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                                       @PathVariable final Long announcementId);

	@Operation(summary = "[인증] 노티 수정", description = "노티를 수정합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "노티 수정 성공"),
			@ApiResponse(responseCode = "404", description = "해당 노티가 없습니다.")
	})
	NofficeResponse<AnnouncementResponse> updateAnnouncement(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                                         @PathVariable final Long announcementId,
	                                                         @RequestBody final AnnouncementUpdateRequest announcementUpdateRequest);

	@Operation(summary = "[인증] 노티 삭제", description = "노티를 삭제합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "노티 삭제 성공"),
			@ApiResponse(responseCode = "400", description = "노티 삭제에 실패하였습니다.")
	})
	NofficeResponse<Void> deleteAnnouncement(@PathVariable final Long announcementId);

	@Operation(summary = "[인증] 노티에 발급된 투두 조회", description = "노티에 발급된 투두를 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "노티에 발급된 투두 조회 성공"),
			@ApiResponse(responseCode = "404", description = "노티에 발급된 투두가 없습니다.")
	})
	NofficeResponse<TaskResponses> getTasksById(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                            @PathVariable final Long announcementId);

	@Operation(summary = "노티에 발급된 투두 삭제", description = "노티에 발급된 투두를 삭제합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "노티에 발급된 투두 삭제 성공"),
			@ApiResponse(responseCode = "400", description = "노티에 발급된 투두 삭제에 실패하였습니다.")
	})
	NofficeResponse<Void> deleteTaskById(@PathVariable final Long announcementId, @PathVariable final Long taskId);
}
