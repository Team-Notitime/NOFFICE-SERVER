package com.notitime.noffice.domain.category.persistence;

import com.notitime.noffice.domain.category.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByIdIn(List<Long> ids);
}
