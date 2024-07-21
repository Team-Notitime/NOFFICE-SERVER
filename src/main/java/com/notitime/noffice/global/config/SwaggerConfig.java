package com.notitime.noffice.global.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {

		Server localServer = new Server();
		localServer.setDescription("local server");
		localServer.setUrl("http://localhost:8080");

		Server productionServer = new Server();
		productionServer.setDescription("develop server");

		productionServer.setUrl("https://api.noffice.store");

		return new OpenAPI()
				.info(getSwaggerInfo())
				.servers(List.of(localServer, productionServer));
	}

	private Info getSwaggerInfo() {
		return new Info()
				.title("NOFFICE API Docs")
				.description("테스트 프로덕션용 NOFFICE API 명세서입니다.\n" +
						"공통된 응답 형식은 Schemas-NofficeResponseString을 참고해주세요.")
				.version("v1.0");
	}
}