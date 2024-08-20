package com.notitime.noffice.domain.promotion.persistence;

import com.notitime.noffice.domain.promotion.Promotion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
	Optional<Promotion> findByCode(String code);
}
