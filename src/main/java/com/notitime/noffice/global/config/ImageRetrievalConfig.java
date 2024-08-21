package com.notitime.noffice.global.config;

import com.notitime.noffice.api.image.strategy.ImageRetrievalContext;
import com.notitime.noffice.api.image.strategy.ImageRetrievalStrategy;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageRetrievalConfig {

	@Bean
	public ImageRetrievalContext imageRetrievalContext(List<ImageRetrievalStrategy<?>> strategies) {
		return new ImageRetrievalContext(strategies);
	}
}