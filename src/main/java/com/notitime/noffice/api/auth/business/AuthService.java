package com.notitime.noffice.api.auth.business;

import static com.notitime.noffice.global.web.BusinessErrorCode.INVALID_REFRESH_TOKEN_VALUE;
import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_SUPPORTED_LOGIN_PLATFORM;

import com.notitime.noffice.api.auth.business.strategy.SocialAuthContext;
import com.notitime.noffice.api.auth.presentation.dto.request.SocialAuthRequest;
import com.notitime.noffice.api.auth.presentation.dto.response.SocialAuthResponse;
import com.notitime.noffice.api.auth.presentation.dto.response.TokenResponse;
import com.notitime.noffice.auth.jwt.JwtProvider;
import com.notitime.noffice.auth.jwt.JwtValidator;
import com.notitime.noffice.domain.RefreshToken;
import com.notitime.noffice.domain.RefreshTokenRepository;
import com.notitime.noffice.domain.fcmtoken.persistence.FcmTokenRepository;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

	private final SocialAuthContext socialAuthContext;
	private JwtProvider jwtTokenProvider;
	private JwtValidator jwtValidator;
	private final MemberRepository memberRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final FcmTokenRepository fcmTokenRepository;

	public SocialAuthResponse login(final SocialAuthRequest request) {
		if (socialAuthContext.support(request.provider())) {
			return socialAuthContext.doLogin(request);
		}
		throw new BadRequestException(NOT_SUPPORTED_LOGIN_PLATFORM);
	}

	public TokenResponse reissue(final String refreshToken) {
		String parsedRefreshToken = refreshToken.substring("Bearer ".length());
		Long memberId = getAuthorizedMemberId(parsedRefreshToken);
		return TokenResponse.toResponse(jwtTokenProvider.issueTokens(memberId));
	}

	public void logout(Long memberId, final String refreshToken) {
		jwtValidator.validateRefreshToken(refreshToken);
		refreshTokenRepository.deleteByMemberId(memberId);
	}

	public void withdrawal(Long memberId) {
		fcmTokenRepository.deleteByMemberId(memberId);
		refreshTokenRepository.deleteByMemberId(memberId);
		memberRepository.deleteById(memberId);
	}
}
