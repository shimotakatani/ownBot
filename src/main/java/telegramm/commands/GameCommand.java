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
 * create time 26.02.2018
 *
 * @author nponosov
 */
public class GameCommand extends BotCommand {

    public GameCommand() {
        super("game", "Получить данные об игре");
    }

    TestClient client = new TestClient();

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = chat.getUserName();
        if (userName == null || userName.isEmpty()) {
            userName = user.getFirstName() + " " + user.getLastName();
        }

        StringBuilder messageTextBuilder = new StringBuilder("Данные об игре ").append(userName).append("[" + chat.getId() + "]");
        messageTextBuilder.append("\n").append(client.getGameMessage()).append("\n");

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
