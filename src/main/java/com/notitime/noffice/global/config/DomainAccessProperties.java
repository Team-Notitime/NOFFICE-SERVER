package com.notitime.noffice.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class DomainAccessProperties {
	@Value("${server.domain.origin}")
	private String originServerDomain;
	@Value("${server.domain.certified}")
	private String certifiedServerDomain;
}
