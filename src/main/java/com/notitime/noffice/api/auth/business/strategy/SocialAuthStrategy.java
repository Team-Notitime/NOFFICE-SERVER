package com.notitime.noffice.api.auth.business.strategy;

import com.notitime.noffice.domain.SocialAuthProvider;
import com.notitime.noffice.api.auth.presentation.dto.request.SocialAuthRequest;
import com.notitime.noffice.api.auth.presentation.dto.response.SocialAuthResponse;

public interface SocialAuthStrategy {

	SocialAuthResponse login(final SocialAuthRequest request);

	boolean support(SocialAuthProvider provider);
}
