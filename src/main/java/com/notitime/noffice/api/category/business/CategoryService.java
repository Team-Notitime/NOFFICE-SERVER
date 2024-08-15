package com.notitime.noffice.api.category.business;

import com.notitime.noffice.domain.category.persistence.CategoryRepository;
import com.notitime.noffice.response.CategoryResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;

	public CategoryResponses getCategories() {
		return CategoryResponses.from(categoryRepository.findAll());
	}
}
