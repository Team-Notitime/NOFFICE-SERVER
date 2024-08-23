package com.notitime.noffice.api.member.presentation;

import static com.notitime.noffice.global.web.BusinessSuccessCode.DELETE_PROFILE_IMAGE_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.GET_MEMBER_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.POST_LOGIN_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.POST_REISSUE_SUCCESS;

import com.notitime.noffice.api.auth.business.AuthService;
import com.notitime.noffice.api.auth.presentation.dto.request.SocialAuthRequest;
import com.notitime.noffice.api.auth.presentation.dto.response.SocialAuthResponse;
import com.notitime.noffice.api.auth.presentation.dto.response.TokenResponse;
import com.notitime.noffice.api.member.business.MemberService;
import com.notitime.noffice.api.member.presentation.dto.response.MemberResponse;
import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.web.NofficeResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController implements MemberApi {

	private final AuthService authService;
	private final MemberService memberService;

	@PostMapping("/login")
	public NofficeResponse<SocialAuthResponse> login(@RequestBody final SocialAuthRequest socialLoginRequest) {
		return NofficeResponse.success(POST_LOGIN_SUCCESS, authService.login(socialLoginRequest));
	}

	@PostMapping("/reissue")
	public NofficeResponse<TokenResponse> reissue(@RequestHeader("Authorization") final String refreshToken) {
		return NofficeResponse.success(POST_REISSUE_SUCCESS, authService.reissue(refreshToken));
	}

	@GetMapping
	public NofficeResponse<MemberResponse> getById(@AuthMember final Long memberId) {
		return NofficeResponse.success(GET_MEMBER_SUCCESS, memberService.getMember(memberId));
	}

	@DeleteMapping("/profile-image")
	public NofficeResponse<Void> deleteProfileImage(@Parameter(hidden = true) @AuthMember final Long memberId) {
		memberService.deleteProfileImage(memberId);
		return NofficeResponse.success(DELETE_PROFILE_IMAGE_SUCCESS);
	}
}
