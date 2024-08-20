package com.notitime.noffice.api.promotion.business;

import static com.notitime.noffice.global.response.BusinessErrorCode.NOT_FOUND_ORGANIZATION;
import static com.notitime.noffice.global.response.BusinessErrorCode.NOT_FOUND_PROMOTION;

import com.notitime.noffice.api.promotion.presentation.dto.PromotionGrantRequest;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.persistence.OrganizationRepository;
import com.notitime.noffice.domain.promotion.OrganizationPromotion;
import com.notitime.noffice.domain.promotion.Promotion;
import com.notitime.noffice.domain.promotion.persistence.OrganizationPromotionRepository;
import com.notitime.noffice.domain.promotion.persistence.PromotionRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService {

	private final OrganizationPromotionRepository organizationPromotionRepository;
	private final OrganizationRepository organizationRepository;
	private final PromotionRepository promotionRepository;

	public void grantOrganization(PromotionGrantRequest request) {
		Organization organization = organizationRepository.findById(request.organizationId())
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_ORGANIZATION));
		Promotion promotion = promotionRepository.findById(request.promotionId())
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_PROMOTION));
		LocalDateTime defaultEndAt = getDefaultEndAt();
		organizationPromotionRepository.save(OrganizationPromotion.of(organization, promotion, defaultEndAt));
	}

	private LocalDateTime getDefaultEndAt() {
		return LocalDateTime.now().plusYears(3);
	}
}
