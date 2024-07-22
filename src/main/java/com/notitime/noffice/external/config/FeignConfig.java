package com.notitime.noffice.external.config;

import com.notitime.noffice.NofficeApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = NofficeApplication.class)
public class FeignConfig {
}
