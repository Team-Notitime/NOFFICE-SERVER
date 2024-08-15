package com.notitime.noffice.api.announcement.presentation;

import com.notitime.noffice.api.announcement.business.AnnouncementService;
import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.response.BusinessSuccessCode;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.AnnouncementCreateRequest;
import com.notitime.noffice.request.AnnouncementUpdateRequest;
import com.notitime.noffice.response.AnnouncementResponse;
import com.notitime.noffice.response.AnnouncementResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/announcement")
public class AnnouncementController implements AnnouncementApi {

	private final AnnouncementService announcementService;

	@GetMapping
	public NofficeResponse<AnnouncementResponses> getAnnouncements() {
		return NofficeResponse.success(BusinessSuccessCode.GET_ANNOUNCEMENTS_SUCCESS,
				announcementService.getAnnouncements());
	}

	@PostMapping
	public NofficeResponse<AnnouncementResponse> createAnnouncement(
			@RequestBody final AnnouncementCreateRequest announcementCreateRequest) {
		return NofficeResponse.success(BusinessSuccessCode.POST_ANNOUNCEMENT_SUCCESS,
				announcementService.createAnnouncement(announcementCreateRequest));
	}

	@GetMapping("/{announcementId}")
	public NofficeResponse<AnnouncementResponse> readAnnouncement(@AuthMember final Long memberId,
	                                                              @PathVariable final Long announcementId) {
		return NofficeResponse.success(BusinessSuccessCode.GET_ANNOUNCEMENT_SUCCESS,
				announcementService.readAnnouncement(memberId, announcementId));
	}

	@PostMapping("/{announcementId}")
	public NofficeResponse<AnnouncementResponse> updateAnnouncement(@PathVariable final Long announcementId,
	                                                                @RequestBody final AnnouncementUpdateRequest announcementUpdateRequest) {
		return NofficeResponse.success(BusinessSuccessCode.PUT_ANNOUNCEMENT_SUCCESS,
				announcementService.updateAnnouncement(announcementId, announcementUpdateRequest));
	}

	@DeleteMapping("/{announcementId}")
	public NofficeResponse<Void> deleteAnnouncement(@PathVariable final Long announcementId) {
		announcementService.deleteAnnouncement(announcementId);
		return NofficeResponse.success(BusinessSuccessCode.DELETE_ANNOUNCEMENT_SUCCESS);
	}
}
