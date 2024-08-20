package com.notitime.noffice.domain.promotion.persistence;

import com.notitime.noffice.domain.promotion.PromotionImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PromotionImageRepository extends JpaRepository<PromotionImage, Long> {
	
	@Query("SELECT pi FROM PromotionImage pi WHERE pi.promotion.id = :promotionId")
	List<PromotionImage> findAllByPromotionId(Long promotionId);
}