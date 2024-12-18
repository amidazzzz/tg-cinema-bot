package org.amida.tg_bot.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieRequestProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMovieRequest(String movieTitle) {
        kafkaTemplate.send("fetch-movie-requests", movieTitle);
        log.info("Sent movie request to Kafka: {}", movieTitle);
    }
}