package telegramm;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import telegramm.commands.*;
import telegramm.services.Emoji;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 21.02.2018
 *
 * @author nponosov
 */
public class SimpleCommandTelegrammBot extends TelegramLongPollingCommandBot {

    public static final String LOGTAG = "SIMPLE_COMMANDS_HANDLER";
    private String token;
    private String botUserName;

    public SimpleCommandTelegrammBot(String token, String botUserName, DefaultBotOptions options) {
        super(options);
        this.token = token;
        this.botUserName = botUserName;
        register(new SimpleTestCommand());
        register(new TimeCommand());
        register(new GameCommand());
        register(new StartGameCommand());
        register(new NameCommand());
        register(new ScoreCommand());

        SimpleHelpCommand helpCommand = new SimpleHelpCommand(this);
        register(helpCommand);

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.enableMarkdown(true);
            setButtons(commandUnknownMessage);
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
        echoMessage.enableMarkdown(true);
        setButtons(echoMessage);
        echoMessage.setChatId(chatId);
        echoMessage.setText(message);
        try {
            sendMessage(echoMessage);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    public static synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        //KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        //keyboardFirstRow.add(new KeyboardButton("/start"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("/game"));
        keyboardSecondRow.add(new KeyboardButton("/score"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("/name"));
        keyboardSecondRow.add(new KeyboardButton("/help"));

        // Добавляем все строчки клавиатуры в список
       // keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }
}