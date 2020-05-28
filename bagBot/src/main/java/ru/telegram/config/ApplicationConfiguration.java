package ru.telegram.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.TelegramBotsApi;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ApplicationConfiguration {

    private String PROXY;
    private String CHECK;
    private String SOCKS_HOST;
    private String HOST;
    private String SOCKS_PORT;
    private String PORT;

    private String BOT_USER_NAME;
    private String TOKEN;
    private String CHECK_FLAG;
    private String PATH_THE_PHOTO;
    private String TYPE_FILE_PNG;
    private String TYPE_FILE_MP4;


    public String getTYPE_FILE_PNG() {
        return TYPE_FILE_PNG;
    }

    public void setTYPE_FILE_PNG(String TYPE_FILE_PNG) {
        this.TYPE_FILE_PNG = TYPE_FILE_PNG;
    }

    public String getTYPE_FILE_MP4() {
        return TYPE_FILE_MP4;
    }

    public void setTYPE_FILE_MP4(String TYPE_FILE_MP4) {
        this.TYPE_FILE_MP4 = TYPE_FILE_MP4;
    }


    public String getPROXY() {
        return PROXY;
    }

    public String getCHECK() {
        return CHECK;
    }

    public String getSOCKS_HOST() {
        return SOCKS_HOST;
    }

    public String getHOST() {
        return HOST;
    }

    public String getSOCKS_PORT() {
        return SOCKS_PORT;
    }

    public String getPORT() {
        return PORT;
    }

    public String getBOT_USER_NAME() {
        return BOT_USER_NAME;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public String getCHECK_FLAG() {
        return CHECK_FLAG;
    }

    public String getPATH_THE_PHOTO() {
        return PATH_THE_PHOTO;
    }

    public void setPROXY(String PROXY) {
        this.PROXY = PROXY;
    }

    public void setCHECK(String CHECK) {
        this.CHECK = CHECK;
    }

    public void setSOCKS_HOST(String SOCKS_HOST) {
        this.SOCKS_HOST = SOCKS_HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public void setSOCKS_PORT(String SOCKS_PORT) {
        this.SOCKS_PORT = SOCKS_PORT;
    }

    public void setPORT(String PORT) {
        this.PORT = PORT;
    }

    public void setBOT_USER_NAME(String BOT_USER_NAME) {
        this.BOT_USER_NAME = BOT_USER_NAME;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public void setCHECK_FLAG(String CHECK_FLAG) {
        this.CHECK_FLAG = CHECK_FLAG;
    }

    public void setPATH_THE_PHOTO(String PATH_THE_PHOTO) {
        this.PATH_THE_PHOTO = PATH_THE_PHOTO;
    }


    @Bean
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi();
    }

}
