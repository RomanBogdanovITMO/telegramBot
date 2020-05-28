package ru.telegram.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;


@Component
public class BotInitilizer {

    private TelegramBotsApi telegramBotsApi;
    private BagBot bagBot;


    @Autowired
    public BotInitilizer(BagBot bagBot, TelegramBotsApi telegramBotsApi) {
        try {
            telegramBotsApi.registerBot(bagBot);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }

    }
}
