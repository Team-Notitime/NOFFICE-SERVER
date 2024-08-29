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
import com.notitime.noffice.external.openfeign.apple.AppleFeignClient;
import com.notitime.noffice.external.openfeign.apple.AppleIdentityTokenParser;
import com.notitime.noffice.external.openfeign.apple.AppleOAuthProvider;
import com.notitime.noffice.external.openfeign.apple.ApplePublicKeyGenerator;
import com.notitime.noffice.external.openfeign.apple.dto.ApplePublicKeys;
import com.notitime.noffice.external.openfeign.dto.AuthorizedMemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AppleAuthStrategy implements SocialAuthStrategy {

	private final AppleFeignClient appleFeignClient;
	private final AppleOAuthProvider appleOAuthProvider;
	private final JwtProvider jwtProvider;
	private final AppleIdentityTokenParser appleIdentityTokenParser;
	private final ApplePublicKeyGenerator applePublicKeyGenerator;
	private final MemberRepository memberRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	@Value("${oauth.apple.client-id}")
	private String clientId;
	@Value("${oauth.apple.key-id}")
	private String clientSecret;
	@Value("${oauth.apple.grant-type}")
	private String grantType;

	@Override
	public boolean support(SocialAuthProvider provider) {
		return provider.equals(SocialAuthProvider.APPLE);
	}

	@Override
	@Transactional
	public SocialAuthResponse login(SocialAuthRequest request) {
		ApplePublicKeys applePublicKeys = appleFeignClient.getApplePublicKey();
		AuthorizedMemberInfo memberResponse = appleOAuthProvider.getAppleUserInfo(request.code(),
				request.provider().name(), applePublicKeys);
		Boolean isAlreadyMember = memberRepository.existsBySerialId(memberResponse.serialId());
		Member member = memberRepository.findBySerialId(memberResponse.serialId())
				.orElseGet(() -> Member.createAuthorizedMember(
						memberResponse.serialId(),
						memberResponse.name(),
						memberResponse.email(),
						request.provider(),
						// TODO : 애플 공급자 프로필 사진 제공여부 확인 후 주입
						"https://www.apple.com/ac/structured-data/images/knowledge_graph_logo.png?202106171739"));
		memberRepository.save(member);
		TokenResponse tokenResponse = TokenResponse.toResponse(jwtProvider.issueTokens(member.getId()));
		refreshTokenRepository.save(RefreshToken.of(member, tokenResponse.refreshToken()));
		return SocialAuthResponse.of(member.getId(), member.getName(), request.provider(), isAlreadyMember,
				tokenResponse);
	}
}
