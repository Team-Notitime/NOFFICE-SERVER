package com.notitime.noffice.api.member.presentation;

import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.web.NofficeResponse;
import com.notitime.noffice.api.auth.presentation.dto.request.SocialAuthRequest;
import com.notitime.noffice.api.member.presentation.dto.response.MemberResponse;
import com.notitime.noffice.api.auth.presentation.dto.response.SocialAuthResponse;
import com.notitime.noffice.api.auth.presentation.dto.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "회원", description = "회원 로그인, 정보 조회 API")
interface MemberApi {
	@Operation(summary = "회원 로그인", description = "본문에 소셜 공급자명과 인가코드를 넣어 노피스 서버 로그인을 시도합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "로그인에 성공하였습니다.")
	})
	NofficeResponse<SocialAuthResponse> login(@RequestBody final SocialAuthRequest socialLoginRequest);

	@Operation(summary = "토큰 재발급", description = "리프레시 토큰을 이용해 새로운 액세스 토큰을 발급합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "액세스 토큰 재발급에 성공하였습니다.")
	})
	NofficeResponse<TokenResponse> reissue(@RequestHeader("Authorization") final String refreshToken);

	@Operation(summary = "[인증] 단일 회원 정보 조회", description = "회원의 정보를 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "회원 정보 조회에 성공하였습니다.")
	})
	NofficeResponse<MemberResponse> getById(@Parameter(hidden = true) @AuthMember final Long memberId);
}