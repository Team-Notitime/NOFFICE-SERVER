package com.notitime.noffice.api.announcement.presentation;

import com.notitime.noffice.api.announcement.presentation.dto.request.AnnouncementCreateRequest;
import com.notitime.noffice.api.announcement.presentation.dto.request.AnnouncementUpdateRequest;
import com.notitime.noffice.api.announcement.presentation.dto.response.AnnouncementResponse;
import com.notitime.noffice.api.announcement.presentation.dto.response.AnnouncementResponses;
import com.notitime.noffice.api.announcement.presentation.dto.response.ReadStatusResponse;
import com.notitime.noffice.api.task.presentation.dto.response.TaskResponses;
import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.external.firebase.FCMCreateResponse;
import com.notitime.noffice.global.web.NofficeResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "노티", description = "노티(조직 내 공지) 관련 API")
interface AnnouncementApi {

	@Hidden
	@Operation(summary = "미사용 - 사용자에게 할당된 모든 노티 조회", description = "사용자에게 할당된 모든 노티를 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "노티 목록 조회 성공"),
			@ApiResponse(responseCode = "404", description = "등록된 노티가 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<AnnouncementResponses> getAnnouncements();

	@Operation(summary = "노티 생성", description = "노티를 생성합니다.", responses = {
			@ApiResponse(responseCode = "201", description = "노티 생성 성공"),
			@ApiResponse(responseCode = "400", description = "노티 생성 실패", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<AnnouncementResponse> create(
			@RequestBody final AnnouncementCreateRequest announcementCreateRequest);

	@Operation(summary = "[인증] 조직에 발급된 노티 열람", description = "열람하려는 노티를 조회하고, 열람 기록에 추가합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "노티 단일 조회 성공"),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "해당 노티가 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<AnnouncementResponse> read(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                           @PathVariable final Long announcementId);

	@Operation(summary = "[인증] 노티 수정", description = "노티를 수정합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "노티 수정 성공"),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "해당 노티가 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<AnnouncementResponse> update(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                             @PathVariable final Long announcementId,
	                                             @RequestBody final AnnouncementUpdateRequest announcementUpdateRequest);

	@Operation(summary = "노티 삭제", description = "노티를 삭제합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "노티 삭제 성공"),
			@ApiResponse(responseCode = "400", description = "노티 삭제에 실패하였습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> delete(@PathVariable final Long announcementId);

	@Operation(summary = "[인증] 노티에 발급된 투두 조회", description = "노티에 발급된 투두를 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "노티에 발급된 투두 조회 성공"),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "노티에 발급된 투두가 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<TaskResponses> getTasksById(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                            @PathVariable final Long announcementId);

	@Operation(summary = "노티에 발급된 투두 삭제", description = "노티에 발급된 투두를 삭제합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "노티에 발급된 투두 삭제 성공"),
			@ApiResponse(responseCode = "400", description = "노티에 발급된 투두 삭제에 실패하였습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> deleteTaskById(@PathVariable final Long announcementId, @PathVariable final Long taskId);

	@Operation(summary = "[인증] 노티 미열람자 대상 FCM 알림", description = "노티 미열람자 대상으로 알림을 전송합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "알림 전송 성공"),
			@ApiResponse(responseCode = "400", description = "알림 전송에 실패하였습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<FCMCreateResponse> sendToUnReader(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                                  @PathVariable final Long announcementId);

	@Operation(summary = "[인증] 공지 열람 사용자 목록 조회 ", description = "공지에 대한 열람 사용자 목록을 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "공지 열람 사용자 목록 조회 성공"),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "공지에 대한 열람 사용자가 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<ReadStatusResponse> getReadMembers(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                                   @PathVariable final Long announcementId);

	@Operation(summary = "[인증] 공지 미열람 사용자 목록 조회 ", description = "공지에 대한 미열람 사용자 목록을 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "공지 미열람 사용자 목록 조회 성공"),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "공지에 대한 미열람 사용자가 없습니다."),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<ReadStatusResponse> getUnReadMembers(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                                     @PathVariable final Long announcementId);

	@Operation(summary = "[인증] 공지 커버 이미지 수정", description = "공지에 설정된 이미지를 변경합니다. 이미지는 선택 가능한 이미지 내에서 설정 가능합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "공지 커버 이미지 수정 성공"),
			@ApiResponse(responseCode = "400", description = "공지 커버 이미지 수정에 실패하였습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "선택 가능한 이미지 주소가 아닙니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> modifyCover(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                  @PathVariable final Long announcementId,
	                                  @RequestBody final String coverImageUrl);

//	@Operation(summary = "[인증] 사용자의 가입된 조직별 할당된 공지 최신 5개 조회 (Paging)", description = "멤버가 가입한 조직 목록과 해당 조직의 최신 공지 5개를 조회합니다.", responses = {
//			@ApiResponse(responseCode = "200", description = "회원의 가입된 조직과 해당 조직의 최신 공지 5개 조회에 성공하였습니다."),
//			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
//			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
//			@ApiResponse(responseCode = "404", description = "가입된 조직이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
//			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
//	})
//	NofficeResponse<Slice<OrganizationResponse>> getAssignedAnnouncements(
//			@Parameter(hidden = true) @AuthMember final Long memberId,
//			Pageable pageable);
}
