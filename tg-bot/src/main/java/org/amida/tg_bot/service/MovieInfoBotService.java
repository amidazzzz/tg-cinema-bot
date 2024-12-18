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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieInfoBotService extends TelegramLongPollingBot {

    private final TelegramBotConfig botConfig;
    private final MovieRequestProducer movieRequestProducer;
    private final Map<String, String> userMovieRequests = new ConcurrentHashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String messageText = update.getMessage().getText();

            log.info("Received message: {}", messageText);

            if (messageText.startsWith("/")) {
                handleCommand(chatId, messageText);
            } else {
                sendMessage(chatId, "Команда не распознана. Используйте /help для списка команд.");
            }
        }
    }

    private void handleCommand(String chatId, String command) {
        if (command.startsWith("/movie")) {
            String[] parts = command.split(" ", 2);

            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                sendMessage(chatId, "Пожалуйста, укажите название фильма после команды /movie.\n" +
                        "Например: /movie Inception");
            } else {
                String movieTitle = parts[1].trim();
                handleMovieRequest(chatId, movieTitle);
            }
        } else {
            switch (command) {
                case "/start":
                    sendMessage(chatId, """
                            Добро пожаловать в Movie Info Bot! 🎥
                            Отправьте мне название фильма, чтобы получить информацию о нём.
                            Команды:
                            /start — приветствие
                            /help — справка
                            /movie <название фильма> — поиск фильма.""");
                    break;
                case "/help":
                    sendMessage(chatId, """
                            Список доступных команд:
                            /start - Приветственное сообщение
                            /help - Показать справку
                            /movie <название фильма> - Получить информацию о фильме""");
                    break;
                default:
                    sendMessage(chatId, "Неизвестная команда. 🤔\nИспользуйте /help для списка команд.");
                    break;
            }
        }
    }

    private void handleMovieRequest(String chatId, String movieTitle) {
        sendMessage(chatId, "Ищу информацию о фильме: " + movieTitle);

        userMovieRequests.put(chatId, movieTitle);

        movieRequestProducer.sendMovieRequest(movieTitle);
    }

    public void handleMovieData(Movie movie) {
        userMovieRequests.forEach((chatId, requestedMovie) -> {
            if (requestedMovie.equalsIgnoreCase(movie.getTitle())) {
                String response = String.format(
                        "🎬 Фильм: %s\n📅 Год: %s\n📜 Жанр: %s\n🎥 Режиссёр: %s",
                        movie.getTitle(), movie.getYear(), movie.getGenre(), movie.getDirector()
                );

                sendMessage(chatId, response);

                userMovieRequests.remove(chatId);
            }
        });
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);

        try {
            execute(message);
        } catch (Exception e) {
            log.error("Не удалось отправить сообщение в Telegram: {}", e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
