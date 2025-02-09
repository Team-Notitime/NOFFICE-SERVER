package com.notitime.noffice.api.auth.business.strategy;

import com.notitime.noffice.api.auth.presentation.dto.request.SocialAuthRequest;
import com.notitime.noffice.api.auth.presentation.dto.response.SocialAuthResponse;
import com.notitime.noffice.api.auth.presentation.dto.response.TokenResponse;
import com.notitime.noffice.auth.jwt.JwtProvider;
import com.notitime.noffice.domain.RefreshToken;
import com.notitime.noffice.domain.RefreshTokenRepository;
import com.notitime.noffice.domain.SocialAuthProvider;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.external.openfeign.google.GoogleApiClient;
import com.notitime.noffice.external.openfeign.google.GoogleAuthApiClient;
import com.notitime.noffice.external.openfeign.google.dto.GoogleInfoResponse;
import com.notitime.noffice.external.openfeign.google.dto.GoogleTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleAuthStrategy implements SocialAuthStrategy {

	@Value("${oauth.google.client-id}")
	private String googleClientId;
	@Value("${oauth.google.client-secret}")
	private String googleClientSecret;
	@Value("${oauth.google.redirect-uri}")
	private String googleRedirectUri;
	@Value("${oauth.google.grant-type}")
	private String googleGrantType;

	private final GoogleAuthApiClient googleAuthApiClient;
	private final GoogleApiClient googleApiClient;

	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	@Override
	public boolean support(SocialAuthProvider provider) {
		return provider.equals(SocialAuthProvider.GOOGLE);
	}

	@Override
	public SocialAuthResponse login(SocialAuthRequest request) {
		GoogleTokenResponse googleTokenResponse = googleAuthApiClient.googleAuth(
				request.code(),
				googleClientId,
				googleClientSecret,
				googleRedirectUri,
				googleGrantType,
				"profile email"
		);
		GoogleInfoResponse memberResponse = googleApiClient.googleInfo("Bearer" + googleTokenResponse.access_token());
		Boolean isAlreadyMember = memberRepository.existsBySerialId(memberResponse.sub());
		Member member = memberRepository.findBySerialId(memberResponse.sub())
				.orElseGet(() -> Member.createAuthorizedMember(
						memberResponse.sub(),
						memberResponse.name(),
						memberResponse.email(),
						request.provider(),
						memberResponse.picture()));
		memberRepository.save(member);
		TokenResponse tokenResponse = TokenResponse.toResponse(jwtProvider.issueTokens(member.getId()));
		if (!isAlreadyMember) {
			refreshTokenRepository.save(RefreshToken.of(member, tokenResponse.refreshToken()));
		}
		return SocialAuthResponse.of(member.getId(), member.getName(), request.provider(), isAlreadyMember,
				tokenResponse);
	}
}
