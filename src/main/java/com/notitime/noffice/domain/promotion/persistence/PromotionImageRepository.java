package com.notitime.noffice.domain.promotion.persistence;

import com.notitime.noffice.domain.promotion.PromotionImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionImageRepository extends JpaRepository<PromotionImage, Long> {
}