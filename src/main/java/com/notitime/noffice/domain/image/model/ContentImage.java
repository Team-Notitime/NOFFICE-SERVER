package com.notitime.noffice.domain.image.model;

import com.notitime.noffice.api.image.business.dto.ImagePurpose;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ContentImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 1000)
	private String title;

	@Column(name = "image_url", length = 1000)
	private String imageUrl;

	@Column(name = "image_type")
	@Enumerated(EnumType.STRING)
	private ImagePurpose imagePurpose;

	public ContentImage(String title, String imageUrl, ImagePurpose imagePurpose) {
		this.title = title;
		this.imageUrl = imageUrl;
		this.imagePurpose = imagePurpose;
	}
}
