package telegramBot.controllers.textHadler;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.controllers.CommandsHandler;
import telegramBot.controllers.CommunicationMode;
import telegramBot.controllers.OutputsMethods;
import telegramBot.models.services.ServiceFiles;

import java.io.IOException;
import java.text.ParseException;

public interface TextHandler {
    public void textHandlerToCommunicationMode(Update update) throws TelegramApiException, IOException, ParseException;

    default Boolean checkCommand(Update update, String textMessage) {
        if (update.getMessage().getText().startsWith("/")) {
            new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(), textMessage);
            new ServiceFiles().setCommunicationMode(update, CommunicationMode.DEFAULT);
            new CommandsHandler().setCommandsHandler(update);
            return false;
        } else return true;
    }
}
