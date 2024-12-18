package org.amida.data_fetcher.service;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

class CinemaServiceTest {

    @Test
    void fetchMovieDataTest() {

        WebClient webClient = WebClient.builder()
                .baseUrl("http://www.omdbapi.com/")
                .build();
        CinemaService cinemaService = new CinemaService(webClient);

        var movieData = cinemaService.fetchMovieData("Inception");

        assertNotNull(movieData);
        assertTrue(movieData.contains("Title"));

    }
}