package com.notitime.noffice.api.image.strategy;

import static com.notitime.noffice.api.image.business.dto.ImagePurpose.ANNOUNCEMENT_PROFILE;

import com.notitime.noffice.api.image.presentation.dto.CommonImageResponse;
import com.notitime.noffice.domain.announcement.model.AnnouncementImage;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnnouncementCoverImageRetrievalStrategy implements ImageRetrievalStrategy<AnnouncementImage> {

	private final AnnouncementImageRepository announcementImageRepository;

	@Override
	public List<CommonImageResponse> getSelectableImages(Long organizationId) {
		return announcementImageRepository.findAll().stream()
				.map(this::toResponse)
				.toList();
	}

	@Override
	public CommonImageResponse toResponse(AnnouncementImage image) {
		return new CommonImageResponse(ANNOUNCEMENT_PROFILE, image.getId(), image.getImageUrl());
	}
}
