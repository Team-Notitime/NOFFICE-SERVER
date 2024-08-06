package com.notitime.noffice.api.notification.presentation;

import com.notitime.noffice.global.response.BusinessSuccessCode;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController implements NotificationApi {

	@PostMapping
	public NofficeResponse<Void> createNotification(@RequestBody final NotificationRequest request) {
		return NofficeResponse.success(BusinessSuccessCode.CREATED_NOTIFICATION_SUCCESS);
	}

	@PostMapping("/bulk")
	public NofficeResponse<Void> createBulkNotification(@RequestBody final NotificationBulkRequest request) {
		return NofficeResponse.success(BusinessSuccessCode.CREATED_BULK_NOTIFICATION_SUCCESS);
	}

	@GetMapping
	public NofficeResponse<Void> getNotifications(@RequestParam final Long memberId) {
		return NofficeResponse.success(BusinessSuccessCode.OK);
	}

	@PatchMapping
	public NofficeResponse<NotificationTimeChangeResponse> changeSendTime(
			@RequestBody final NotificationTimeChangeRequest request) {
		return NofficeResponse.success(BusinessSuccessCode.CHANGE_SEND_TIME_SUCCESS);
	}

	@DeleteMapping("/{notificationId}")
	public NofficeResponse<Void> deleteNotification(@PathVariable final Long notificationId) {
		return NofficeResponse.success(BusinessSuccessCode.DELETE_NOTIFICATION_SUCCESS);
	}
}
