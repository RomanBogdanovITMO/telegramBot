package ru.telegram;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Application {

    /*public Application(ApplicationConfiguration myConfig) {
        this.myConfig = myConfig;
        //простые настройки для обхода блокировки в телеграме
        System.getProperties().put(myConfig.getPROXY(), myConfig.getCHECK());
        System.getProperties().put(myConfig.getSOCKS_HOST(), myConfig.getHOST());
        System.getProperties().put(myConfig.getSOCKS_PORT(), myConfig.getPORT());

    }*/




    public static void main(String[] args) {

        System.getProperties().put("proxySet", "true");
        System.getProperties().put("socksProxyHost", "127.0.0.1");
        System.getProperties().put("socksProxyPort", "9150");

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

        System.out.println("Bot start");

    }
}
