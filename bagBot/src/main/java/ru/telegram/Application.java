package ru.telegram;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.telegram.config.ApplicationConfiguration;

@SpringBootApplication
public class Application {

    @Autowired
    private ApplicationConfiguration myConfig;

    /*public Application(ApplicationConfiguration myConfig) {
        this.myConfig = myConfig;
        //простые настройки для обхода блокировки в телеграме
        System.getProperties().put(myConfig.getPROXY(), myConfig.getCHECK());
        System.getProperties().put(myConfig.getSOCKS_HOST(), myConfig.getHOST());
        System.getProperties().put(myConfig.getSOCKS_PORT(), myConfig.getPORT());

    }*/

    static {
        ApiContextInitializer.init();
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
