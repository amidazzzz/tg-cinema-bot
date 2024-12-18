package org.amida.tg_bot.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amida.tg_bot.model.Movie;
import org.amida.tg_bot.service.MovieInfoBotService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieDataConsumer {

    private final MovieInfoBotService movieInfoBotService;

    @KafkaListener(topics = "movie-data-responses", groupId = "telegram-bot-group")
    public void consumeMovieData(Movie movie){
        log.info("Received movie data from Kafka {}", movie);
        movieInfoBotService.handleMovieData(movie);
    }
}
