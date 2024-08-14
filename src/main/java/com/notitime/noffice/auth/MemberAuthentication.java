package com.notitime.noffice.auth;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class MemberAuthentication extends UsernamePasswordAuthenticationToken {

	private MemberAuthentication(
			Object principal,
			Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	public static MemberAuthentication createMemberAuthentication(Long memberId) {
		return new MemberAuthentication(memberId, null, null);
	}
}
