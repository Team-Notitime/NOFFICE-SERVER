package com.notitime.noffice.api.announcement.presentation;

import static com.notitime.noffice.global.web.BusinessSuccessCode.DELETE_ANNOUNCEMENT_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.DELETE_TASK_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.GET_ANNOUNCEMENTS_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.GET_ANNOUNCEMENT_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.GET_READ_MEMBERS_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.GET_TASKS_BY_ANNOUNCEMENT_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.GET_UNREAD_MEMBERS_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.PATCH_MODIFY_COVER_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.POST_ANNOUNCEMENT_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.PUT_ANNOUNCEMENT_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.SEND_UNREADER_REMIND_SUCCSS;

import com.notitime.noffice.api.announcement.business.AnnouncementService;
import com.notitime.noffice.api.announcement.presentation.dto.request.AnnouncementCreateRequest;
import com.notitime.noffice.api.announcement.presentation.dto.request.AnnouncementUpdateRequest;
import com.notitime.noffice.api.announcement.presentation.dto.response.AnnouncementResponse;
import com.notitime.noffice.api.announcement.presentation.dto.response.AnnouncementResponses;
import com.notitime.noffice.api.announcement.presentation.dto.response.ReadStatusResponse;
import com.notitime.noffice.api.task.business.TaskService;
import com.notitime.noffice.api.task.presentation.dto.response.TaskResponses;
import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.external.firebase.FCMCreateResponse;
import com.notitime.noffice.external.firebase.FcmService;
import com.notitime.noffice.global.web.NofficeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	private final TaskService taskService;
	private final FcmService fcmService;

	@GetMapping
	public NofficeResponse<AnnouncementResponses> getAnnouncements() {
		return NofficeResponse.success(GET_ANNOUNCEMENTS_SUCCESS,
				announcementService.getAnnouncements());
	}

	@PostMapping
	public NofficeResponse<AnnouncementResponse> create(
			@RequestBody final AnnouncementCreateRequest announcementCreateRequest) {
		return NofficeResponse.success(POST_ANNOUNCEMENT_SUCCESS,
				announcementService.create(announcementCreateRequest));
	}

	@GetMapping("/{announcementId}")
	public NofficeResponse<AnnouncementResponse> read(@AuthMember final Long memberId,
	                                                  @PathVariable final Long announcementId) {
		return NofficeResponse.success(GET_ANNOUNCEMENT_SUCCESS,
				announcementService.read(memberId, announcementId));
	}

	@PostMapping("/{announcementId}")
	public NofficeResponse<AnnouncementResponse> update(@AuthMember final Long memberId,
	                                                    @PathVariable final Long announcementId,
	                                                    @RequestBody final AnnouncementUpdateRequest announcementUpdateRequest) {
		return NofficeResponse.success(PUT_ANNOUNCEMENT_SUCCESS,
				announcementService.updateAnnouncement(announcementId, announcementUpdateRequest));
	}

	@DeleteMapping("/{announcementId}")
	public NofficeResponse<Void> delete(@PathVariable final Long announcementId) {
		announcementService.deleteAnnouncement(announcementId);
		return NofficeResponse.success(DELETE_ANNOUNCEMENT_SUCCESS);
	}

	@GetMapping("/{announcementId}/tasks")
	public NofficeResponse<TaskResponses> getTasksById(@AuthMember final Long memberId,
	                                                   @PathVariable final Long announcementId) {
		return NofficeResponse.success(GET_TASKS_BY_ANNOUNCEMENT_SUCCESS,
				taskService.getTasksById(announcementId));
	}

	@DeleteMapping("/{announcementId}/tasks/{taskId}")
	public NofficeResponse<Void> deleteTaskById(@PathVariable final Long announcementId,
	                                            @PathVariable final Long taskId) {
		taskService.delete(announcementId, taskId);
		return NofficeResponse.success(DELETE_TASK_SUCCESS);
	}

	@PostMapping("/{announcementId}/remind-unread")
	public NofficeResponse<FCMCreateResponse> sendToUnReader(@AuthMember final Long memberId,
	                                                         @PathVariable final Long announcementId) {
		return NofficeResponse.success(SEND_UNREADER_REMIND_SUCCSS,
				fcmService.sendToUnReader(memberId, announcementId));
	}

	@GetMapping("/{announcementId}/readers")
	public NofficeResponse<ReadStatusResponse> getReadMembers(@AuthMember final Long memberId,
	                                                          @PathVariable final Long announcementId) {
		return NofficeResponse.success(GET_READ_MEMBERS_SUCCESS,
				announcementService.getReadMembers(memberId, announcementId));
	}

	@GetMapping("/{announcementId}/unreaders")
	public NofficeResponse<ReadStatusResponse> getUnReadMembers(@AuthMember final Long memberId,
	                                                            @PathVariable final Long announcementId) {
		return NofficeResponse.success(GET_UNREAD_MEMBERS_SUCCESS,
				announcementService.getUnreadMembers(memberId, announcementId));
	}

	@PatchMapping("/{announcementId}/cover")
	public NofficeResponse<Void> modifyCover(@AuthMember final Long memberId, @PathVariable final Long announcementId,
	                                         @RequestBody final String coverImageUrl) {
		announcementService.modifyCover(memberId, announcementId, coverImageUrl);
		return NofficeResponse.success(PATCH_MODIFY_COVER_SUCCESS);
	}
}
