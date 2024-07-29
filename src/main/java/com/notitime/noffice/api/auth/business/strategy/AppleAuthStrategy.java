package com.notitime.noffice.api.auth.business.strategy;

import com.notitime.noffice.auth.jwt.JwtProvider;
import com.notitime.noffice.domain.SocialAuthProvider;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.external.openfeign.apple.AppleFeignClient;
import com.notitime.noffice.external.openfeign.apple.AppleOAuthProvider;
import com.notitime.noffice.external.openfeign.apple.dto.AppleTokenResponse;
import com.notitime.noffice.external.openfeign.dto.AuthorizedMemberInfo;
import com.notitime.noffice.request.SocialAuthRequest;
import com.notitime.noffice.response.SocialAuthResponse;
import com.notitime.noffice.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppleAuthStrategy implements SocialAuthStrategy {

	private final AppleFeignClient appleFeignClient;
	private final AppleOAuthProvider appleOAuthProvider;
	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;

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
		AuthorizedMemberInfo memberResponse = appleOAuthProvider.getAppleUserInfo(appleTokenResponse.idToken(),
				request.provider().name(), applePublicKeys);
		Member member = memberRepository.findBySerialId(memberResponse.serialId())
				.orElseGet(() -> Member.createAuthorizedMember(
						memberResponse.serialId(),
						memberResponse.name(),
						memberResponse.email()));
		memberRepository.save(member);
		TokenResponse tokenResponse = TokenResponse.toResponse(jwtProvider.issueTokens(member.getId()));
		return SocialAuthResponse.of(member.getId(), member.getName(), request.provider(), tokenResponse);
	}
}
