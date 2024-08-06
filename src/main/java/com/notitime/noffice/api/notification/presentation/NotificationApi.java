package com.notitime.noffice.api.notification.presentation;

import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.NotificationRequest;
import com.notitime.noffice.request.NotificationTimeChangeRequest;
import com.notitime.noffice.response.NotificationBulkRequest;
import com.notitime.noffice.response.NotificationTimeChangeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "알림", description = "노티 알림 발송 관련 API")
@RequestMapping("/api/v1/notifications")
interface NotificationApi {

	@Operation(summary = "단일 사용자 알림 대기열 추가", description = "단일 사용자를 특정하여 노티 알림 대기열에 등록합니다.", responses = {
			@ApiResponse(responseCode = "NOF-20710", description = "알림 대기열 등록 성공"),
			@ApiResponse(responseCode = "NOF-400", description = "알림 발송 실패")})
	@PostMapping
	NofficeResponse<Void> createNotification(@RequestBody final NotificationRequest request);

	@Operation(summary = "조직 단위 알림 대량 발송", description = "조직 내 모든 사용자에게 전체 발송되는 알림을 등록합니다.", responses = {
			@ApiResponse(responseCode = "NOF-20711", description = "조직 전체 알림 대량 등록 성공"),
			@ApiResponse(responseCode = "NOF-400", description = "조직 전체 알림 대량 등록 실패")})
	@PostMapping("/bulk")
	NofficeResponse<Void> createBulkNotification(@RequestBody final NotificationBulkRequest request);

	@Operation(summary = "사용자에게 수신된 알림 조회", description = "사용자에게 수신된 알림을 조회합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2071", description = "알림 조회 성공"),
			@ApiResponse(responseCode = "NOF-404", description = "알림이 없습니다.")
	})
	@GetMapping
	NofficeResponse<Void> getNotifications(@RequestParam final Long memberId);

	@Operation(summary = "알림 발송 시간 변경", description = "노티 알림 발송 시간을 변경합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2072", description = "알림 발송 시간 변경 성공"),
			@ApiResponse(responseCode = "NOF-400", description = "알림 발송 시간 변경 실패")
	})
	@PatchMapping
	NofficeResponse<NotificationTimeChangeResponse> changeSendTime(
			@RequestBody final NotificationTimeChangeRequest request);

	@Operation(summary = "알림 삭제", description = "노티 알림을 삭제합니다.", responses = {
			@ApiResponse(responseCode = "NOF-2073", description = "알림 삭제 성공"),
			@ApiResponse(responseCode = "NOF-400", description = "알림 삭제 실패")
	})
	@DeleteMapping("/{notificationId}")
	NofficeResponse<Void> deleteNotification(@PathVariable final Long notificationId);
}
