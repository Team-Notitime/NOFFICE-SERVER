package com.notitime.noffice.api.auth.business.strategy;

import com.notitime.noffice.domain.SocialAuthProvider;
import com.notitime.noffice.global.exception.BadRequestException;
import com.notitime.noffice.global.response.BusinessErrorCode;
import com.notitime.noffice.request.SocialAuthRequest;
import com.notitime.noffice.response.SocialAuthResponse;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocialAuthContext {

	private AppleAuthStrategy appleAuthStrategy;
	private final List<SocialAuthStrategy> socialAuthStrategies = new ArrayList<>();

	@PostConstruct
	void initSocialLoginContext() {
		socialAuthStrategies.add(appleAuthStrategy);
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