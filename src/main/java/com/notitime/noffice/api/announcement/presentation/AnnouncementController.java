package com.notitime.noffice.api.announcement.presentation;

import com.notitime.noffice.api.announcement.business.AnnouncementService;
import com.notitime.noffice.global.response.BusinessSuccessCode;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.AnnouncementCreateRequest;
import com.notitime.noffice.request.AnnouncementUpdateRequest;
import com.notitime.noffice.response.AnnouncementResponse;
import com.notitime.noffice.response.AnnouncementResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "노티", description = "노티(공지사항) CRUD API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/announcement")
public class AnnouncementController {

	private final AnnouncementService announcementService;

	@Operation(summary = "모든 노티 조회", description = "사용자에게 할당된 모든 노티를 조회합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2051", description = "노티 목록 조회 성공"),
			@ApiResponse(responseCode = "NOF-404", description = "등록된 노티가 없습니다.")
	})
	@GetMapping
	public NofficeResponse<AnnouncementResponses> getAnnouncements() {
		return NofficeResponse.success(BusinessSuccessCode.GET_ANNOUNCEMENTS_SUCCESS,
				announcementService.getAnnouncements());
	}

	@Operation(summary = "노티 생성", description = "노티를 생성합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2150", description = "노티 생성 성공"),
			@ApiResponse(responseCode = "NOF-400", description = "노티 생성 실패")
	})
	@PostMapping
	public NofficeResponse<AnnouncementResponse> createAnnouncement(
			@RequestBody final AnnouncementCreateRequest announcementCreateRequest) {
		return NofficeResponse.success(BusinessSuccessCode.POST_ANNOUNCEMENT_SUCCESS,
				announcementService.createAnnouncement(announcementCreateRequest));
	}

	@Operation(summary = "노티 조회", description = "노티를 조회합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2050", description = "노티 단일 조회 성공"),
			@ApiResponse(responseCode = "NOF-404", description = "해당 노티가 없습니다.")
	})
	@GetMapping("/{announcementId}")
	public NofficeResponse<AnnouncementResponse> getAnnouncement(@PathVariable final Long announcementId) {
		return NofficeResponse.success(BusinessSuccessCode.GET_ANNOUNCEMENT_SUCCESS,
				announcementService.getAnnouncement(announcementId));
	}

	@Operation(summary = "노티 수정", description = "노티를 수정합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2052", description = "노티 수정 성공"),
			@ApiResponse(responseCode = "NOF-404", description = "해당 노티가 없습니다.")
	})
	@PostMapping("/{announcementId}")
	public NofficeResponse<AnnouncementResponse> updateAnnouncement(@PathVariable final Long announcementId,
	                                                                @RequestBody final AnnouncementUpdateRequest announcementUpdateRequest) {
		return NofficeResponse.success(BusinessSuccessCode.PUT_ANNOUNCEMENT_SUCCESS,
				announcementService.updateAnnouncement(announcementId, announcementUpdateRequest));
	}

	@Operation(summary = "노티 삭제", description = "노티를 삭제합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2053", description = "노티 삭제 성공"),
			@ApiResponse(responseCode = "NOF-404", description = "노티 삭제에 실패하였습니다.")
	})
	@DeleteMapping("/{announcementId}")
	public NofficeResponse<Void> deleteAnnouncement(@PathVariable final Long announcementId) {
		announcementService.deleteAnnouncement(announcementId);
		return NofficeResponse.success(BusinessSuccessCode.DELETE_ANNOUNCEMENT_SUCCESS);
	}
}
