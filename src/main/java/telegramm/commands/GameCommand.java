package telegramm.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import spring.dto.MessageDto;
import spring.restClient.TestClient;
import telegramm.consts.TagNameConst;
import telegramm.services.Emoji;
import telegramm.services.MapDeserializationService;

/**
 * create time 26.02.2018
 *
 * @author nponosov
 */
public class GameCommand extends BotCommand {

    public GameCommand() {
        super("game", "Получить данные об вашей игре с зайцем");
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
        MessageDto messageDto = client.getGameMessage(chat.getId());
        String clientMessage = messageDto.mapString;
        if (clientMessage.length() > 0) {
            messageTextBuilder.append("\n")
                    .append(MapDeserializationService.getMapDeserialization(clientMessage))
                    .append("\n");
            messageTextBuilder.append(messageDto.timeString).append("\n");
            messageTextBuilder.append(messageDto.message);

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
