package telegramm;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import telegramm.commands.GameCommand;
import telegramm.commands.SimpleHelpCommand;
import telegramm.commands.SimpleTestCommand;
import telegramm.commands.TimeCommand;
import telegramm.services.Emoji;

/**
 * create time 21.02.2018
 *
 * @author nponosov
 */
public class SimpleCommandTelegrammBot extends TelegramLongPollingCommandBot {

    public static final String LOGTAG = "SIMPLE_COMMANDS_HANDLER";
    private String token;
    private String botUserName;

    public SimpleCommandTelegrammBot(String token, String botUserName) {
        this.token = token;
        this.botUserName = botUserName;
        register(new SimpleTestCommand());
        register(new TimeCommand());
        register(new GameCommand());

        SimpleHelpCommand helpCommand = new SimpleHelpCommand(this);
        register(helpCommand);

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId());
            commandUnknownMessage.setText("The command '" + message.getText() + "' is not known by this bot. Here comes some help " + Emoji.AMBULANCE);
            try {
                absSender.sendMessage(commandUnknownMessage);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
        });
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onClosing() {

    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                SendMessage echoMessage = new SendMessage();
                echoMessage.setChatId(message.getChatId());
                echoMessage.setText("Hey heres your message:\n" + message.getText());

                try {
                    sendMessage(echoMessage);
                } catch (TelegramApiException e) {
                    BotLogger.error(LOGTAG, e);
                }
            }
        }
    }

    /**
     * Отправка сообщения в чат
     * @param chatId - идентификатор чата
     * @param message - текст сообщения
     */
    public void sendMessage(Long chatId, String message) {
        SendMessage echoMessage = new SendMessage();
        echoMessage.setChatId(chatId);
        echoMessage.setText(message);
        try {
            sendMessage(echoMessage);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}