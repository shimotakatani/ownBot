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

import static telegramm.SimpleCommandTelegrammBot.setButtons;

/**
 * create time 09.03.2018
 *
 * @author nponosov
 */
public class ScoreCommand extends BotCommand {

    public ScoreCommand() {
        super("score", "Показывает счёт для всех зайцев");
    }

    TestClient client = new TestClient();

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = chat.getUserName();
        if (userName == null || userName.isEmpty()) {
            userName = user.getFirstName() + " " + user.getLastName();
        }
        SendMessage answer = new SendMessage();

        StringBuilder messageTextBuilder = new StringBuilder("Общий счёт: \n\n");
        String clientMessage = client.getScoreMessage(chat.getId());
        if (clientMessage.length() > 0) {
            messageTextBuilder.append(clientMessage);
            answer.setChatId(chat.getId().toString());
            answer.setText(messageTextBuilder.toString());
        } else {
            answer.setChatId(chat.getId().toString());
            answer.setText(clientMessage);
        }
        try {
//            answer.enableMarkdown(true);
//            setButtons(answer);
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(TagNameConst.LOGTAG_TEST, e);
        }

    }
}