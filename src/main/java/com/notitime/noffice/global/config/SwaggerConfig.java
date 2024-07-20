package com.notitime.noffice.global.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI api() {
		Server server = new Server().url("/");

		return new OpenAPI()
				.info(getSwaggerInfo())
				.addServersItem(server);
	}

	private Info getSwaggerInfo() {
		return new Info()
				.title("NOFFICE API Docs")
				.description("TEST: NOFFICE API Docs")
				.version("v1.0");
	}
}