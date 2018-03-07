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
import telegramm.services.MapDeserializationService;

/**
 * create time 22.02.2018
 *
 * @author nponosov
 */
public class StartGameCommand extends BotCommand {
    public StartGameCommand() {
        super("start", "Начинаем игру с зайцем");
    }

    TestClient client = new TestClient();

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = chat.getUserName();
        if (userName == null || userName.isEmpty()) {
            userName = user.getFirstName() + " " + user.getLastName();
        }
        SendMessage answer = new SendMessage();
        StringBuilder messageTextBuilder = new StringBuilder("Данные об игре ").append(userName).append("[" + chat.getId() + "]");
        String clientMessage = client.getStartMessage(chat.getId());
        if (clientMessage.contains("\n")) {
            String mapString = clientMessage.substring(0, clientMessage.lastIndexOf("\n"));
            String rabbitString = clientMessage.substring(clientMessage.lastIndexOf("\n"), clientMessage.length());
            messageTextBuilder.append("\n")
                    .append(MapDeserializationService.getMapDeserialization(mapString))
                    .append("\n");
            messageTextBuilder.append(rabbitString);

            answer.setChatId(chat.getId().toString());
            answer.setText(messageTextBuilder.toString());
        } else {
            answer.setChatId(chat.getId().toString());
            answer.setText(clientMessage);
        }
        try {
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(TagNameConst.LOGTAG_TEST, e);
        }
    }
}
