package com.notitime.noffice.external.openfeign.google;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "googleClient", url = "${client.google-auth.url}", configuration = GoogleFeignClientConfiguration.class)
public class GoogleFeignClient {
}
