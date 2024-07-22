package com.notitime.noffice.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record OrganizationCreateRequest(
		@NotNull(message = "그룹의 이름을 입력해주세요.")
		String name,
		@NotNull(message = "그룹의 카테고리를 선택해주세요.")
		CategoryRequest categories,
		@Nullable
		String profile_image,
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		@Nullable
		LocalDate organizationEndAt,
		@Nullable
		String promotion_code
) {
}