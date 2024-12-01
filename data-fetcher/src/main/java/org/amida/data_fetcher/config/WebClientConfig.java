package org.amida.data_fetcher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {

    @Value("${API.URL}")
    private String apiUrl;

    @Bean
    public WebClient webClientBuilder(WebClient.Builder builder) {
        return builder.baseUrl(apiUrl).build();
    }
}
