package com.notitime.noffice.external.openfeign.google;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;

public class GoogleFeignClientConfiguration {

	@Bean
	public GoogleFeignClientErrorDecoder googleFeignClientErrorDecoder() {
		return new GoogleFeignClientErrorDecoder(new ObjectMapper());
	}
}
