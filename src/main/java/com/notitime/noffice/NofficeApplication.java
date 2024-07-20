package com.notitime.noffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NofficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NofficeApplication.class, args);
	}

}
