package com.notitime.noffice.external.openfeign.apple.dto;

public record AuthorizedMemberInfo(
		String serialId,
		String name,
		String email) {
	public static AuthorizedMemberInfo of(final String serialId, final String name, final String email) {
		return new AuthorizedMemberInfo(serialId, name, email);
	}
}
