package com.notitime.noffice.domain.organization.model;

import com.notitime.noffice.global.exception.BadRequestException;
import com.notitime.noffice.global.web.BusinessErrorCode;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Nickname {

	private static final int MAX_LENGTH = 10;

	private String nickname;

	public Nickname(String nickname) {
		validate(nickname);
		this.nickname = nickname;
	}

	private void validate(final String nickname) {
		if (nickname.length() > MAX_LENGTH) {throw new BadRequestException(BusinessErrorCode.INVALID_NICKNAME_LENGTH);}
	}
}
