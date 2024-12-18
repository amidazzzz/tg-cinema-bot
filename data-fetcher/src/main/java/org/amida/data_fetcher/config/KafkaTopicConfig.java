package org.amida.data_fetcher.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic movieRequestTopic() {
        return new NewTopic("fetch-movie-requests", 1, (short) 1);
    }

    @Bean
    public NewTopic movieResponseTopic() {
        return new NewTopic("movie-data-responses", 1, (short) 1);
    }
}
