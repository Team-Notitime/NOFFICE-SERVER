package com.notitime.noffice.api.notification.presentation;

import static com.notitime.noffice.global.web.BusinessSuccessCode.CHANGE_SEND_TIME_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.CREATED_BULK_NOTIFICATION_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.CREATED_NOTIFICATION_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.DELETE_NOTIFICATION_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.OK;
import static com.notitime.noffice.global.web.BusinessSuccessCode.POST_SAVE_FCM_TOKEN_SUCCESS;

import com.notitime.noffice.api.notification.business.NotificationService;
import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.web.NofficeResponse;
import com.notitime.noffice.api.notification.presentation.dto.request.NotificationRequest;
import com.notitime.noffice.api.notification.presentation.dto.request.NotificationTimeChangeRequest;
import com.notitime.noffice.api.notification.presentation.dto.request.NotificationBulkRequest;
import com.notitime.noffice.api.notification.presentation.dto.response.NotificationTimeChangeResponse;
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
@RequestMapping("/api/v1/notifications")
public class NotificationController implements NotificationApi {

	private final NotificationService notificationService;

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

	@PostMapping("/fcm-token")
	public NofficeResponse<Void> saveFcmToken(@AuthMember final Long memberId,
	                                          @RequestBody final String fcmToken) {
		notificationService.saveFcmToken(memberId, fcmToken);
		return NofficeResponse.success(POST_SAVE_FCM_TOKEN_SUCCESS);
	}
}