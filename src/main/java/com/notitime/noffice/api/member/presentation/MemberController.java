package com.notitime.noffice.api.member.presentation;

import com.notitime.noffice.api.auth.business.AuthService;
import com.notitime.noffice.api.member.business.MemberService;
import com.notitime.noffice.global.response.BusinessSuccessCode;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.SocialAuthRequest;
import com.notitime.noffice.response.MemberResponse;
import com.notitime.noffice.response.OrganizationResponses;
import com.notitime.noffice.response.SocialAuthResponse;
import com.notitime.noffice.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원", description = "회원 로그인, 정보 조회 API")
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

	private final AuthService authService;
	private final MemberService memberService;

	@Operation(summary = "회원 로그인", description = "인증 헤더에 토큰을, 본문에 소셜 로그인 정보를 넣어 노피스 서버 로그인을 시도합니다.")
	@PostMapping("/login")
	public NofficeResponse<SocialAuthResponse> login(@RequestHeader("Authorization") final String authorization,
	                                                 @RequestBody final SocialAuthRequest socialLoginRequest) {
		return NofficeResponse.success(BusinessSuccessCode.POST_LOGIN_SUCCESS, authService.login(socialLoginRequest));
	}

	@Operation(summary = "토큰 재발급", description = "리프레시 토큰을 이용해 새로운 액세스 토큰을 발급합니다.")
	@PostMapping("/reissue")
	public NofficeResponse<TokenResponse> reissue(@RequestHeader("Authorization") final String refreshToken) {
		return NofficeResponse.success(BusinessSuccessCode.POST_REISSUE_SUCCESS, authService.reissue(refreshToken));
	}

	@Operation(summary = "단일 회원 정보 조회")
	@GetMapping("/{memberId}")
	public NofficeResponse<MemberResponse> getMember(@PathVariable final Long memberId) {
		return NofficeResponse.success(BusinessSuccessCode.GET_MEMBER_SUCCESS, memberService.getMember(memberId));
	}

	@Operation(summary = "멤버가 가입한 조직 목록 조회")
	@GetMapping("/{memberId}/organizations")
	public NofficeResponse<OrganizationResponses> getJoinedOrganizations(@PathVariable final Long memberId) {
		return NofficeResponse.success(BusinessSuccessCode.GET_JOINED_ORGANIZATIONS_SUCCESS,
				memberService.getJoinedOrganizations(memberId));
	}
}
