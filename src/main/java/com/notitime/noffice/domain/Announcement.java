package com.notitime.noffice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Announcement extends BaseTimeEntity {

	@Id
	private Long id;

	private String title;

	private String content;

	private String coverImage;

	private String place_link_title;

	private String place_link_url;

	private LocalDateTime startAt;

	private LocalDateTime endAt;

	private LocalDateTime noticeAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id")
	private Organization organization;
}
