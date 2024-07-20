package com.notitime.noffice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Organization extends BaseTimeEntity {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private LocalDateTime endAt;

	private String profileImage;
}
