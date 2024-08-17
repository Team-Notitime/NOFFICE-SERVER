package com.notitime.noffice.api.notification.presentation;

import static com.notitime.noffice.global.response.BusinessSuccessCode.CHANGE_SEND_TIME_SUCCESS;
import static com.notitime.noffice.global.response.BusinessSuccessCode.CREATED_BULK_NOTIFICATION_SUCCESS;
import static com.notitime.noffice.global.response.BusinessSuccessCode.CREATED_NOTIFICATION_SUCCESS;
import static com.notitime.noffice.global.response.BusinessSuccessCode.DELETE_NOTIFICATION_SUCCESS;
import static com.notitime.noffice.global.response.BusinessSuccessCode.OK;

import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.NotificationRequest;
import com.notitime.noffice.request.NotificationTimeChangeRequest;
import com.notitime.noffice.response.NotificationBulkRequest;
import com.notitime.noffice.response.NotificationTimeChangeResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController implements NotificationApi {

	@PostMapping
	public NofficeResponse<Void> create(@AuthMember final Long memberId,
	                                    @RequestBody final NotificationRequest request) {
		return NofficeResponse.success(CREATED_NOTIFICATION_SUCCESS);
	}

	@PostMapping("/bulk")
	public NofficeResponse<Void> createAll(@AuthMember final Long memberId,
	                                       @RequestBody final NotificationBulkRequest request) {
		return NofficeResponse.success(CREATED_BULK_NOTIFICATION_SUCCESS);
	}

	@GetMapping
	public NofficeResponse<Void> findById(@AuthMember final Long memberId) {
		return NofficeResponse.success(OK);
	}

	@PatchMapping
	public NofficeResponse<NotificationTimeChangeResponse> changeSendTime(@AuthMember final Long memberId,
	                                                                      @RequestBody final NotificationTimeChangeRequest request) {
		return NofficeResponse.success(CHANGE_SEND_TIME_SUCCESS);
	}

	@DeleteMapping("/{notificationId}")
	public NofficeResponse<Void> delete(@PathVariable final Long notificationId) {
		return NofficeResponse.success(DELETE_NOTIFICATION_SUCCESS);
	}
}
