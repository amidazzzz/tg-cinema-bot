package org.amida.data_fetcher.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amida.data_fetcher.model.Movie;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieProducerService {

    private final KafkaTemplate<String, Movie> kafkaTemplate;


    public void send(String topic, String key, Movie movie) {
        kafkaTemplate.send(topic, key, movie);
        log.info("Sent movie data to topic {} with key {}", topic, key);
    }
}
