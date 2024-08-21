package com.notitime.noffice.api.notification.presentation;

import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.NotificationRequest;
import com.notitime.noffice.request.NotificationTimeChangeRequest;
import com.notitime.noffice.response.NotificationBulkRequest;
import com.notitime.noffice.response.NotificationTimeChangeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "알림", description = "노티 알림 발송 관련 API")
interface NotificationApi {

	@Operation(summary = "[인증] 단일 사용자 알림 대기열 추가", description = "단일 사용자를 특정하여 노티 알림 대기열에 등록합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "알림 대기열 등록 성공"),
			@ApiResponse(responseCode = "400", description = "알림 발송 실패")
	})
	NofficeResponse<Void> create(@Parameter(hidden = true) @AuthMember final Long memberId,
	                             @RequestBody final NotificationRequest request);

	@Operation(summary = "[인증] 조직 단위 알림 대량 발송", description = "조직 내 모든 사용자에게 전체 발송되는 알림을 등록합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "조직 전체 알림 대량 등록 성공"),
			@ApiResponse(responseCode = "400", description = "조직 전체 알림 대량 등록 실패")
	})
	NofficeResponse<Void> createAll(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                @RequestBody final NotificationBulkRequest request);

	@Operation(summary = "[인증] 사용자에게 수신된 알림 조회", description = "사용자에게 수신된 알림을 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "알림 조회 성공"),
			@ApiResponse(responseCode = "404", description = "알림이 없습니다.")
	})
	NofficeResponse<Void> findById(@Parameter(hidden = true) @AuthMember final Long memberId);

	@Operation(summary = "[인증] 알림 발송 시간 변경", description = "노티 알림 발송 시간을 변경합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "알림 발송 시간 변경 성공"),
			@ApiResponse(responseCode = "400", description = "알림 발송 시간 변경 실패")
	})
	NofficeResponse<NotificationTimeChangeResponse> changeSendTime(
			@Parameter(hidden = true) @AuthMember final Long memberId,
			@RequestBody final NotificationTimeChangeRequest request);

	@Operation(summary = "알림 삭제", description = "노티 알림을 삭제합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "알림 삭제 성공"),
			@ApiResponse(responseCode = "400", description = "알림 삭제 실패")
	})
	NofficeResponse<Void> delete(@PathVariable final Long notificationId);

	@Operation(summary = "기기별 FCM Token 저장", description = "기기별 FCM Token을 저장합니다.", responses = {
			@ApiResponse(responseCode = "201", description = "FCM Token 저장 성공"),
			@ApiResponse(responseCode = "400", description = "FCM Token 저장 실패")
	})
	NofficeResponse<Void> saveFcmToken(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                   @RequestBody final String fcmToken);
}
