package telegramm.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import telegramm.consts.TagNameConst;

import java.util.Calendar;
import java.util.Date;

/**
 * create time 21.02.2018
 *
 * @author nponosov
 */
public class TimeCommand extends BotCommand {

    private static final long millsInHour = 1000L * 60L * 60L;

    public TimeCommand() {
        super("time", "Команда, которая говорит который час");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        String userName = chat.getUserName();
        if (userName == null || userName.isEmpty()) {
            userName = user.getFirstName() + " " + user.getLastName();
        }

        StringBuilder messageTextBuilder = new StringBuilder("Hello ").append(userName).append("["+chat.getId()+"]");

        Calendar c=Calendar.getInstance();
        Date timeForShow = c.getTime();
        if (arguments != null && arguments.length > 1){
            String firstArgument = arguments[0];
            try {
                long l = Long.parseLong(firstArgument);
                if (arguments[1].contains("час") || arguments[1].contains("hour")) {
                    timeForShow = new Date(timeForShow.getTime() + millsInHour * l);
                }
            } catch (NumberFormatException nfe) {
                //то не число, значит просто игнорим
            }
        }

        messageTextBuilder.append("\n");
        messageTextBuilder.append("Сейчас:\n");
        messageTextBuilder.append(String.join(" ", timeForShow.toString()));


        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageTextBuilder.toString());

        try {
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(TagNameConst.LOGTAG_TIME, e);
        }

    }
}
