package com.notitime.noffice.api.announcement.presentation;

import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.AnnouncementCreateRequest;
import com.notitime.noffice.request.AnnouncementUpdateRequest;
import com.notitime.noffice.response.AnnouncementResponse;
import com.notitime.noffice.response.AnnouncementResponses;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "노티", description = "노티(조직 내 공지) 관련 API")
public interface AnnouncementApi {

	@Hidden
	@Operation(summary = "미사용 - 사용자에게 할당된 모든 노티 조회", description = "사용자에게 할당된 모든 노티를 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "노티 목록 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "등록된 노티가 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<AnnouncementResponses> getAnnouncements();

	@Operation(summary = "노티 생성", description = "노티를 생성합니다.", responses = {
			@ApiResponse(responseCode = "201", description = "노티 생성 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "400", description = "노티 생성 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<AnnouncementResponse> createAnnouncement(
			@RequestBody final AnnouncementCreateRequest announcementCreateRequest);

	@Operation(summary = "노티 조회", description = "노티를 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "노티 단일 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "해당 노티가 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<AnnouncementResponse> getAnnouncement(@PathVariable final Long announcementId);

	@Operation(summary = "노티 수정", description = "노티를 수정합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "노티 수정 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "해당 노티가 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<AnnouncementResponse> updateAnnouncement(@PathVariable final Long announcementId,
	                                                         @RequestBody final AnnouncementUpdateRequest announcementUpdateRequest);

	@Operation(summary = "노티 삭제", description = "노티를 삭제합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "노티 삭제 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "400", description = "노티 삭제에 실패하였습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> deleteAnnouncement(@PathVariable final Long announcementId);
}
