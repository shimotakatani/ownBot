package telegramm.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import spring.restClient.TestClient;
import telegramm.consts.TagNameConst;

/**
 * create time 22.02.2018
 *
 * @author nponosov
 */
public class StartGameCommand extends BotCommand {
    public StartGameCommand() {
        super("start", "Начинаем игру с самолётом");
    }

    TestClient client = new TestClient();

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = chat.getUserName();
        if (userName == null || userName.isEmpty()) {
            userName = user.getFirstName() + " " + user.getLastName();
        }

        StringBuilder messageTextBuilder = new StringBuilder("Привет ").append(userName).append("["+chat.getId()+"]");
        if (arguments != null && arguments.length > 0) {
            messageTextBuilder.append("\n");
            messageTextBuilder.append("Спасибо Вам за эти слова:\n");
            messageTextBuilder.append(String.join(" ", arguments));
            messageTextBuilder.append("\n").append(client.getTestMessage(arguments[0])).append("\n");
        }

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageTextBuilder.toString());

        try {
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(TagNameConst.LOGTAG_TEST, e);
        }
    }
}
