package com.notitime.noffice.global.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "서버 헬스 체크", description = "노피스 서버 상태 확인 API")
@RestController
public class HealthApi {

	@Operation(summary = "서버 상태 확인", description = "정상 동작시 200 반환합니다.")
	@GetMapping("/health")
	public ResponseEntity<Void> health() {
		return ResponseEntity.ok().build();
	}
}
