package com.notitime.noffice.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "서버 헬스 체크", description = "노피스 서버 상태 확인 API")
@RestController
public class HealthController {

	@Operation(summary = "서버 상태 확인", description = "정상 동작시 OK 반환합니다.")
	@GetMapping("/health")
	public String health() {
		return "OK";
	}
}
