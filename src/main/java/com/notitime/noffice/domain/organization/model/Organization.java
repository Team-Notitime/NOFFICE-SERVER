package com.notitime.noffice.domain.organization.model;

import com.notitime.noffice.domain.BaseTimeEntity;
import jakarta.persistence.Column;
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

	@Column(columnDefinition = "TEXT")
	private String profileImage;
}
