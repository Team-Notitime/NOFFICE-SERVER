package com.notitime.noffice.domain.promotion.persistence;

import com.notitime.noffice.domain.promotion.OrganizationPromotion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationPromotionRepository extends JpaRepository<OrganizationPromotion, Long> {
	Optional<OrganizationPromotion> findByOrganizationIdAndPromotionId(Long organizationId, Long promotionId);
}
