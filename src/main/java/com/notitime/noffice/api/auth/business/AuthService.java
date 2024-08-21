package com.notitime.noffice.api.auth.business;

import com.notitime.noffice.api.auth.business.strategy.SocialAuthContext;
import com.notitime.noffice.auth.jwt.JwtProvider;
import com.notitime.noffice.auth.jwt.JwtValidator;
import com.notitime.noffice.domain.RefreshToken;
import com.notitime.noffice.domain.RefreshTokenRepository;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.global.exception.BadRequestException;
import com.notitime.noffice.global.web.BusinessErrorCode;
import com.notitime.noffice.api.auth.presentation.dto.request.SocialAuthRequest;
import com.notitime.noffice.api.auth.presentation.dto.response.SocialAuthResponse;
import com.notitime.noffice.api.auth.presentation.dto.response.TokenResponse;
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

	public SocialAuthResponse login(final SocialAuthRequest request) {
		if (socialAuthContext.support(request.provider())) {
			return socialAuthContext.doLogin(request);
		}
		throw new BadRequestException(BusinessErrorCode.NOT_SUPPORTED_LOGIN_PLATFORM);
	}

	public TokenResponse reissue(final String refreshToken) {
		String parsedRefreshToken = refreshToken.substring("Bearer ".length());
		Long memberId = getAuthorizedMemberId(parsedRefreshToken);
		return TokenResponse.toResponse(jwtTokenProvider.issueTokens(memberId));
	}

	public Long getAuthorizedMemberId(String parsedRefreshToken) {
		jwtValidator.validateRefreshToken(parsedRefreshToken);
		RefreshToken storedRefreshToken = refreshTokenRepository.findByRefreshToken(parsedRefreshToken)
				.orElseThrow(() -> new BadRequestException(BusinessErrorCode.INVALID_REFRESH_TOKEN_VALUE));
		return storedRefreshToken.getMember().getId();
	}
}
