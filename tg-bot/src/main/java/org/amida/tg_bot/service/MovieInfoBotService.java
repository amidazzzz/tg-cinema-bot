package org.amida.tg_bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amida.tg_bot.config.TelegramBotConfig;
import org.amida.tg_bot.kafka.MovieRequestProducer;
import org.amida.tg_bot.model.Movie;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieInfoBot extends TelegramLongPollingBot {

    private final TelegramBotConfig botConfig;
    private final MovieRequestProducer movieRequestProducer;


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            String chatId = update.getMessage().getChatId().toString();
            String movieTitle = update.getMessage().getText();

//            log.info("Received message from user: {}", movieTitle);

            movieRequestProducer.sendMovieRequest(movieTitle);
            sendMessage(chatId, "Searching for the movie: " + movieTitle);
        }
    }

    public void handleMovieData(Movie movie) {
        String response = String.format(
                "Movie: %s\nYear: %s\nGenre: %s\nDirector: %s",
                movie.getTitle(), movie.getYear(), movie.getGenre(), movie.getDirector()
        );

        sendMessage(movie.getTitle(), response);
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);

        try {
            execute(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
//            log.error("Failed to send message to Telegram: {}", e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }
}
