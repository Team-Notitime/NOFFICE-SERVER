package com.notitime.noffice.external.firebase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FCMNotificationConstants {

	SPECIFIED_MEMBER_TITLE("님의 새로운 노피스 알림"),
	SPECIFIED_MEMBER_CONTENT("그룹에서 메시지가 도착했어요."),
	ANNOUNCE_CREATE_TITLE_SUFFIX(" 의 새로운 노티"),
	ANNOUNCE_CREATE_BODY_PREFIX("노피스에 "),
	ANNOUNCE_CREATE_BODY_SUFFIX(" 공지가 등록되었습니다.");

	private final String value;
}