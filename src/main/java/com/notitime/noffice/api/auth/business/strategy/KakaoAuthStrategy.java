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
import com.notitime.noffice.external.openfeign.kakao.KakaoUserInfoClient;
import com.notitime.noffice.external.openfeign.kakao.dto.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthStrategy implements SocialAuthStrategy {

	@Value("${oauth.kakao.client-id}")
	private String kakaoClientId;
	@Value("${oauth.kakao.client-secret}")
	private String kakaoClientSecret;

	private final KakaoUserInfoClient kakaoUserInfoClient;

	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	@Override
	public boolean support(SocialAuthProvider provider) {
		return provider.equals(SocialAuthProvider.KAKAO);
	}

	@Override
	public SocialAuthResponse login(SocialAuthRequest request) {
		// TODO: revise Access token to OIDC token
		KakaoUserResponse userResponse = kakaoUserInfoClient.getUserInformation("Bearer " + request.code());
		Boolean isAlreadyMember = memberRepository.existsBySerialId(userResponse.id());
		Member member = memberRepository.findBySerialId(userResponse.id())
				.orElseGet(() -> Member.createAuthorizedMember(
						userResponse.id(),
						userResponse.kakaoAccount().profile().nickname(),
						null,
						request.provider(),
						userResponse.kakaoAccount().profile().profileImageUrl()));
		memberRepository.save(member);
		TokenResponse tokenResponse = TokenResponse.toResponse(jwtProvider.issueTokens(member.getId()));
		if (!isAlreadyMember) {
			refreshTokenRepository.save(RefreshToken.of(member, tokenResponse.refreshToken()));
		}
		return SocialAuthResponse.of(member.getId(), member.getName(), request.provider(), isAlreadyMember,
				tokenResponse);
	}
}
