package com.notitime.noffice.auth;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasAuthMemberAnnotation = parameter.hasParameterAnnotation(AuthMember.class);
		boolean isLongType = parameter.getParameterType().equals(Long.class);
		return hasAuthMemberAnnotation && isLongType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
	                              NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		return SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();
	}
}