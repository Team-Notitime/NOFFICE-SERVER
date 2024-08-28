package com.notitime.noffice.api.notification.presentation.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record SaveTokenRequest(
		@Schema(name = "FCM 토큰", example = "(token)", requiredMode = REQUIRED)
		String token) {
}
