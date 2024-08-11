package com.notitime.noffice.api.auth.business.strategy;

import com.notitime.noffice.auth.jwt.JwtProvider;
import com.notitime.noffice.domain.SocialAuthProvider;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.external.openfeign.apple.AppleFeignClient;
import com.notitime.noffice.external.openfeign.apple.AppleIdentityTokenParser;
import com.notitime.noffice.external.openfeign.apple.AppleOAuthProvider;
import com.notitime.noffice.external.openfeign.apple.ApplePublicKeyGenerator;
import com.notitime.noffice.external.openfeign.apple.dto.ApplePublicKeys;
import com.notitime.noffice.external.openfeign.apple.dto.AppleTokenResponse;
import com.notitime.noffice.external.openfeign.dto.AuthorizedMemberInfo;
import com.notitime.noffice.request.SocialAuthRequest;
import com.notitime.noffice.response.SocialAuthResponse;
import com.notitime.noffice.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleAuthStrategy implements SocialAuthStrategy {

	private final AppleFeignClient appleFeignClient;
	private final AppleOAuthProvider appleOAuthProvider;
	private final JwtProvider jwtProvider;
	private final AppleIdentityTokenParser appleIdentityTokenParser;
	private final ApplePublicKeyGenerator applePublicKeyGenerator;
	private final MemberRepository memberRepository;

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
	public SocialAuthResponse login(SocialAuthRequest request) {
		AppleTokenResponse appleTokenResponse = appleFeignClient.getAppleTokens(
				request.code(),
				clientId,
				clientSecret,
				grantType);
		ApplePublicKeys applePublicKeys = appleFeignClient.getApplePublicKey();
		AuthorizedMemberInfo memberResponse = appleOAuthProvider.getAppleUserInfo(appleTokenResponse.idToken(),
				request.provider().name(), applePublicKeys);
		Member member = memberRepository.findBySerialId(memberResponse.serialId())
				.orElseGet(() -> Member.createAuthorizedMember(
						memberResponse.serialId(),
						memberResponse.name(),
						memberResponse.email(),
						request.provider(),
						// TODO : 애플 공급자 프로필 사진 제공여부 확인 후 주입
						""));
		memberRepository.save(member);
		TokenResponse tokenResponse = TokenResponse.toResponse(jwtProvider.issueTokens(member.getId()));
		return SocialAuthResponse.of(member.getId(), member.getName(), request.provider(), tokenResponse);
	}
}
