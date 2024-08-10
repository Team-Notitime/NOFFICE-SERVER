package com.notitime.noffice.api.announcement.presentation;

import com.notitime.noffice.auth.LoginUser;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.AnnouncementCreateRequest;
import com.notitime.noffice.request.AnnouncementUpdateRequest;
import com.notitime.noffice.response.AnnouncementCoverResponse;
import com.notitime.noffice.response.AnnouncementResponse;
import com.notitime.noffice.response.AnnouncementResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "노티", description = "노티(조직 내 공지) 관련 API")
public interface AnnouncementApi {

	@Operation(summary = "모든 노티 조회", description = "사용자에게 할당된 모든 노티를 조회합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2051", description = "노티 목록 조회 성공"),
			@ApiResponse(responseCode = "NOF-404", description = "등록된 노티가 없습니다.")})
	NofficeResponse<AnnouncementResponses> getAnnouncements();

	@Operation(summary = "노티 생성", description = "노티를 생성합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2150", description = "노티 생성 성공"),
			@ApiResponse(responseCode = "NOF-400", description = "노티 생성 실패")})
	NofficeResponse<AnnouncementResponse> createAnnouncement(
			@RequestBody final AnnouncementCreateRequest announcementCreateRequest);

	@Operation(summary = "노티 조회", description = "노티를 조회합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2050", description = "노티 단일 조회 성공"),
			@ApiResponse(responseCode = "NOF-404", description = "해당 노티가 없습니다.")})
	NofficeResponse<AnnouncementResponse> getAnnouncement(@PathVariable final Long announcementId);

	@Operation(summary = "노티 수정", description = "노티를 수정합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2052", description = "노티 수정 성공"),
			@ApiResponse(responseCode = "NOF-404", description = "해당 노티가 없습니다.")})
	NofficeResponse<AnnouncementResponse> updateAnnouncement(@PathVariable final Long announcementId,
	                                                         @RequestBody final AnnouncementUpdateRequest announcementUpdateRequest);

	@Operation(summary = "노티 삭제", description = "노티를 삭제합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2053", description = "노티 삭제 성공"),
			@ApiResponse(responseCode = "NOF-404", description = "노티 삭제에 실패하였습니다.")})
	NofficeResponse<Void> deleteAnnouncement(@PathVariable final Long announcementId);

	@Operation(summary = "노티에 발행된 알림 개수 조회", description = "노티에 발행된 알림 개수를 조회합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2072", description = "알림 개수 조회 성공"),
			@ApiResponse(responseCode = "NOF-404", description = "노티에 발행된 알림이 없습니다.")})
	NofficeResponse<Integer> getNotificationCount(@PathVariable final Long announcementId);

	@Operation(summary = "조직별 노티 페이징 조회", description = "조직별 노티를 페이징 조회합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2073", description = "조직별 노티 페이징 조회 성공"),
			@ApiResponse(responseCode = "NOF-404", description = "조직에 등록된 노티가 없습니다.")})
	NofficeResponse<Slice<AnnouncementCoverResponse>> getPublishedAnnouncements(@LoginUser final Long memberId,
	                                                                            @PathVariable final Long organizationId,
	                                                                            Pageable pageable);
}
