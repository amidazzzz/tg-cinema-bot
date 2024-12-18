package org.amida.tg_bot.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic movieRequestTopic() {
        return new NewTopic("fetch-movie-requests", 1, (short) 1);
    }
}
