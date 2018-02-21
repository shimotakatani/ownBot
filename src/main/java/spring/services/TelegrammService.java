package spring.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import telegramm.SimpleCommandTelegrammBot;

/**
 * create time 21.02.2018
 *
 * @author nponosov
 */
@Scope("singleton")
@Service
public class TelegrammService {
    public static final Logger LOG = LoggerFactory.getLogger(TelegrammService.class);

    private TelegramBotsApi telegramBotsApi;
    private SimpleCommandTelegrammBot simpleCommandTelegrammBot;

    public void initTelegrammBot(String token, String botUserName){
        if (telegramBotsApi == null) {
            telegramBotsApi = new TelegramBotsApi();
            try {
                simpleCommandTelegrammBot = new SimpleCommandTelegrammBot(token, botUserName);
                telegramBotsApi.registerBot(simpleCommandTelegrammBot);
            } catch (TelegramApiException e) {
                LOG.error(e.getMessage(),e);
            }
        }
    }

    public void sendMessage(String message, Long chatId){
        simpleCommandTelegrammBot.sendMessage(chatId, message);
    }}
