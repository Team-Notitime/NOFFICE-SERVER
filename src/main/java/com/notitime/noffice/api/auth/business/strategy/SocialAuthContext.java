package com.notitime.noffice.api.auth.business.strategy;

import com.notitime.noffice.api.auth.presentation.dto.request.SocialAuthRequest;
import com.notitime.noffice.api.auth.presentation.dto.response.SocialAuthResponse;
import com.notitime.noffice.domain.SocialAuthProvider;
import com.notitime.noffice.global.exception.BadRequestException;
import com.notitime.noffice.global.web.BusinessErrorCode;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocialAuthContext {

	private final GoogleAuthStrategy googleAuthStrategy;
	private final AppleAuthStrategy appleAuthStrategy;
	private final KakaoAuthStrategy kakaoAuthStrategy;
	private final List<SocialAuthStrategy> socialAuthStrategies = new ArrayList<>();

	@PostConstruct
	void initSocialLoginContext() {
		socialAuthStrategies.add(appleAuthStrategy);
		socialAuthStrategies.add(googleAuthStrategy);
		socialAuthStrategies.add(kakaoAuthStrategy);
	}

	public boolean support(SocialAuthProvider provider) {
		for (SocialAuthStrategy strategy : socialAuthStrategies) {
			if (strategy.support(provider)) {
				return true;
			}
		}
		return false;
	}

	public SocialAuthResponse doLogin(final SocialAuthRequest request) {
		for (SocialAuthStrategy strategy : socialAuthStrategies) {
			if (strategy.support(request.provider())) {
				return strategy.login(request);
			}
		}
		throw new BadRequestException(BusinessErrorCode.NOT_SUPPORTED_LOGIN_PLATFORM);
	}
}