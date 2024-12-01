package org.amida.data_fetcher.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amida.data_fetcher.model.Movie;
import org.amida.data_fetcher.service.CinemaService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieConsumerService {

    private final CinemaService cinemaService;
    private final MovieProducerService movieProducerService;
    private final ObjectMapper mapper;


    @KafkaListener(topics = "fetch-movie-requests", groupId = "data-fetcher-group")
    public void consume(String title){
        log.info("Received movie title: {}", title);
        var jsonData = cinemaService.fetchMovieData(title);

        Movie movie = parseMovieData(jsonData);

        movieProducerService.send("movie-data-responses", title, movie);
    }

    public Movie parseMovieData(String movieData) {
        try {
            return mapper.readValue(movieData, Movie.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse movie data", e);
        }
    }
}
