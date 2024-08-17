package com.notitime.noffice.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Value("${server.domain.certified}")
	private String serverDomainCertified;

	@Bean
	public OpenAPI openAPI() {

		Server localServer = new Server();
		localServer.setDescription("local server");
		localServer.setUrl("http://localhost:8080");

		Server productionServer = new Server();
		productionServer.setDescription("develop server");
		productionServer.setUrl(serverDomainCertified);

		SecurityScheme apiKey = new SecurityScheme()
				.type(SecurityScheme.Type.HTTP)
				.in(SecurityScheme.In.HEADER)
				.name("Authorization")
				.scheme("bearer")
				.bearerFormat("JWT");

		SecurityRequirement securityRequirement = new SecurityRequirement()
				.addList("Bearer Token");
		Components components = new Components()
				.addSecuritySchemes("Bearer Token", apiKey)
				.addSchemas("SortObject", createSortSchema());

		return new OpenAPI()
				.info(getSwaggerInfo())
				.components(components)
				.addSecurityItem(securityRequirement)
				.servers(List.of(productionServer, localServer));
	}

	private Schema<?> createSortSchema() {
		return new ArraySchema()
				.items(new ObjectSchema()
						.addProperty("sorted", new StringSchema().example("true"))
						.addProperty("unsorted", new StringSchema().example("false"))
						.addProperty("empty", new StringSchema().example("false")));
	}

	private Info getSwaggerInfo() {
		return new Info()
				.title("NOFFICE API Docs")
				.description("테스트 프로덕션용 NOFFICE API 명세서입니다.\n")
				.version("v1.0");
	}
}