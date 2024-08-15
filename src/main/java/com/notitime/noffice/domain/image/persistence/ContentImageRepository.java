package com.notitime.noffice.domain.image.persistence;

import com.notitime.noffice.domain.image.model.ContentImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentImageRepository extends JpaRepository<ContentImage, Long> {
}
