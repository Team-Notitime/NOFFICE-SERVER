package com.notitime.noffice.api.image.business.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PresignedUrlInfoVO(
		@Schema(description = "사용자가 업로드할 파일을 저장할 URL", required = true, example = "https://example.com")
		String url
) {
	public static PresignedUrlInfoVO of(String url) {
		return new PresignedUrlInfoVO(url);
	}
}