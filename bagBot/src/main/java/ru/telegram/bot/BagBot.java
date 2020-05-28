package ru.telegram.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendVideo;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.telegram.config.ApplicationConfiguration;
import ru.telegram.model.QuestionAndAnswer;
import ru.telegram.servis.SequenceGeneratorService;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Component
public class BagBot extends TelegramLongPollingBot {
    private final static Logger logger = Logger.getLogger(BagBot.class.getName());

    @Autowired
    private ApplicationConfiguration myConfig;

    private SequenceGeneratorService service;


    public BagBot(SequenceGeneratorService service, ApplicationConfiguration myConfig) {
        this.service = service;
        this.myConfig = myConfig;
    }


    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            List<QuestionAndAnswer> listsQuestionAndA = service.getAll();
            int valueList = listsQuestionAndA.size();
            int count = 0;


            if (message != null && message.hasText()) {
                logger.info("id users: " + message.getChatId());
                String textUser = message.getText();
                logger.info("user request: " + textUser);
                for (QuestionAndAnswer lists : listsQuestionAndA) {
                    count++;
                    if (lists.getQuestion().equals(textUser)) {
                        requestOfUserForBag(textUser, message, listsQuestionAndA);
                        sendMSG(message, getParserString(lists.getAnswer()));
                        logger.info("server response: " + lists.getAnswer());
                        break;
                    } else if (count == valueList) {
                        sendMSG(message, "извините, данная команда в разработке или запрос не корректный");

                    }
                }
            } else if (update.hasCallbackQuery()) {
                try {
                    execute(new SendMessage().setText(
                            update.getCallbackQuery().getData())
                            .setChatId(update.getCallbackQuery().getMessage().getChatId()));
                } catch (TelegramApiException e) {
                    logger.warning(e.getMessage());
                }

            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Message message = callbackQuery.getMessage();
            getVideo(message, callbackQuery.getData());
        }



    }

    //обрабатывается запрос клиента если найдено совпадение , выгружаем список фото данному запросу
    private void requestOfUserForBag(String textUser, Message message, List<QuestionAndAnswer> questionAndAnswerList) {

        for (QuestionAndAnswer list : questionAndAnswerList) {
            if ((list.getQuestion().equals(textUser)) && (list.getAnswer().equals(myConfig.getCHECK_FLAG()))) {
                getListBag(message, list.getAdditionInfo());
                break;
            }
        }

    }

    //получаем список фотографий из dir-static на основе запроса клиента
    private void getListBag(Message message, String questionAndAnswer) {

        String nameDirectory = myConfig.getPATH_THE_PHOTO() + questionAndAnswer;
        File myfile = new File(nameDirectory);
        File[] files = myfile.listFiles();
        List<File> listFiles = Arrays.asList(files);

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());
        sendPhoto.setReplyToMessageId(message.getMessageId());

        for (File list : listFiles) {
            String[] arrm = list.getName().split("\\.");
            for (String str : arrm) {
                if (str.equals("png")) {
                    try {
                        sendPhoto(sendPhoto.setNewPhoto(list));
                        execute(setButtons1(message.getChatId(), list.getPath()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    //создаем новую строку(ответ) взятую из бд(ответ) и возвращаем ответ для клиента
    private String getParserString(String text) {
        String resaultParserString = "";
        for (String str : text.split("_")) {
            String value = str.trim();
            resaultParserString += value + "\n";
        }
        return resaultParserString;
    }

    //отправка сообщения клиенту
    private void sendMSG(Message message, String text) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);

        try {

            setButtons(sendMessage);
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //формируем клавиатуру с кнопками(клавиатура формируется сразу же при нажатии команды /start
    private void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowlist = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSeconfRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/start"));
        keyboardFirstRow.add(new KeyboardButton("/выбрать сумку"));
        keyboardSeconfRow.add(new KeyboardButton("/помощь"));
        keyboardSeconfRow.add(new KeyboardButton("/показать все"));

        keyboardRowlist.add(keyboardFirstRow);
        keyboardRowlist.add(keyboardSeconfRow);

        replyKeyboardMarkup.setKeyboard(keyboardRowlist);
    }

    //формируем клавиатуру с кнопкой(клавиатура не постоянного типа, формируется при просмотре сумок )
    private SendMessage setButtons1(long chatId, String nameFile) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("показать");
        inlineKeyboardButton1.setCallbackData(nameFile);

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Описание сумки").setReplyMarkup(inlineKeyboardMarkup);
    }

    //отправляем видео (видео обзор сумки) в качестве дополнительного описания сумки
    private void getVideo(Message message, String nameFile) {

        String deletValue = myConfig.getTYPE_FILE_PNG();
        String addValue = myConfig.getTYPE_FILE_MP4();
        StringBuffer buffer = new StringBuffer(nameFile);
        buffer.delete(buffer.length() - deletValue.length(), buffer.length());
        buffer.append(addValue);
        String nameFileVideo = buffer.toString();

        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(message.getChatId().toString());
        sendVideo.setReplyToMessageId(message.getMessageId());

        File file = new File(nameFileVideo);
        if (file.isFile()&& file.exists()) {
            try {
                sendVideo(sendVideo.setNewVideo(file));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }else {
            sendMSG(message,"нет описания");
        }
    }


    @Override
    public String getBotUsername() {
        return myConfig.getBOT_USER_NAME();
    }

    @Override
    public String getBotToken() {
        return myConfig.getTOKEN();
    }


}
