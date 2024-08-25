package com.notitime.noffice.api.image.business.dto;

public record ContentImagePresignedUrlVO(
		String fileName,
		PresignedUrlInfoVO urls
) {
	public static ContentImagePresignedUrlVO of(String fileName, PresignedUrlInfoVO urls) {
		return new ContentImagePresignedUrlVO(fileName, urls);
	}
}