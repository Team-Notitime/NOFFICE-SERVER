package com.notitime.noffice.api.member.presentation;

import com.notitime.noffice.api.auth.presentation.dto.request.SocialAuthRequest;
import com.notitime.noffice.api.auth.presentation.dto.request.TokenReissueRequest;
import com.notitime.noffice.api.auth.presentation.dto.response.SocialAuthResponse;
import com.notitime.noffice.api.auth.presentation.dto.response.TokenResponse;
import com.notitime.noffice.api.member.presentation.dto.request.MemberAliasUpdateRequest;
import com.notitime.noffice.api.member.presentation.dto.request.MemberNameUpdateRequest;
import com.notitime.noffice.api.member.presentation.dto.request.MemberProfileUpdateRequest;
import com.notitime.noffice.api.member.presentation.dto.response.MemberResponse;
import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.web.NofficeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "회원", description = "회원 로그인, 정보 조회 API")
interface MemberApi {
	@Operation(summary = "회원 로그인", description = "본문에 소셜 공급자명과 인가코드를 넣어 노피스 서버 로그인을 시도합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "로그인에 성공하였습니다."),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<SocialAuthResponse> login(@RequestBody final SocialAuthRequest socialLoginRequest);

	@Operation(summary = "토큰 재발급", description = "리프레시 토큰을 이용해 새로운 액세스 토큰을 발급합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "액세스 토큰 재발급에 성공하였습니다."),
			@ApiResponse(responseCode = "401", description = "리프레시 토큰이 유효하지 않습니다. 다시 로그인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<TokenResponse> reissue(final TokenReissueRequest tokenReissueRequest);

	@Operation(summary = "[인증] 회원 로그아웃", description = "회원의 계정에 저장된 Fcm 토큰을 모두 삭제합니다. 신규 로그인 시 토큰을 재요청해야합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "로그아웃에 성공하였습니다."),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> logout(@Parameter(hidden = true) @AuthMember final Long memberId,
	                             @RequestHeader("notification-token") final String notificationToken);

	@Operation(summary = "[인증] 회원 탈퇴", description = "회원의 계정을 탈퇴합니다. 탈퇴 시 회원의 모든 정보가 삭제됩니다.", responses = {
			@ApiResponse(responseCode = "204", description = "회원 탈퇴에 성공하였습니다."),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> withdrawal(@Parameter(hidden = true) @AuthMember final Long memberId);

	@Operation(summary = "[인증] 단일 회원 정보 조회", description = "회원의 정보를 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "회원 정보 조회에 성공하였습니다."),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<MemberResponse> getById(@Parameter(hidden = true) @AuthMember final Long memberId);

	@Operation(summary = "[인증] 회원 프로필 이미지 삭제", description = "회원의 프로필 이미지를 기본값(null)으로 되돌립니다.", responses = {
			@ApiResponse(responseCode = "204", description = "회원 프로필 이미지 삭제에 성공하였습니다."),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> deleteProfileImage(@Parameter(hidden = true) @AuthMember final Long memberId);

	@Operation(summary = "[인증] 회원 별명 변경", description = "회원의 별명을 변경합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "회원 별명 변경에 성공하였습니다."),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> updateAlias(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                  @RequestBody final MemberAliasUpdateRequest alias);

	@Operation(summary = "[인증] 회원 프로필 이미지 변경", description = "회원의 프로필 이미지를 변경합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "회원 프로필 이미지 변경에 성공하였습니다."),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> updateMemberProfile(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                          @RequestBody final MemberProfileUpdateRequest request);

	@Operation(summary = "[인증] 회원 이름 변경", description = "회원의 이름을 변경합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "회원 이름 변경에 성공하였습니다."),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> updateName(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                 @RequestBody final MemberNameUpdateRequest request);
}