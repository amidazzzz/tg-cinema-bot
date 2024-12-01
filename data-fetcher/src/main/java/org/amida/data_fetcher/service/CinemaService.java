package org.amida.data_fetcher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class CinemaService {

    @Value("${API.KEY}")
    private final String apiKey;

    private final WebClient webClient;

    public String fetchMovieData(String title){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("t", title)
                        .queryParam("apiKey", apiKey)
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
