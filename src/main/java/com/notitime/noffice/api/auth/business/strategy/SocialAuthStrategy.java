package com.notitime.noffice.api.auth.business.strategy;

import com.notitime.noffice.domain.SocialAuthProvider;
import com.notitime.noffice.request.SocialAuthRequest;
import com.notitime.noffice.response.SocialAuthResponse;

public interface SocialAuthStrategy {

	SocialAuthResponse login(final SocialAuthRequest request);

	boolean support(SocialAuthProvider provider);
}
