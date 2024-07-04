package telegramBot.controllers.textHadler.implementation;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.controllers.CommandsHandler;
import telegramBot.controllers.OutputsMethods;
import telegramBot.controllers.textHadler.TextHandler;

public class TextHandlerDefault implements TextHandler {
    @Override
    public void textHandlerToCommunicationMode(Update update) throws TelegramApiException {
        if (update.getMessage().getText().startsWith("/")) {
            new CommandsHandler().setCommandsHandler(update);
        } else {
            new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(), "Нельзя ввести что-то кроме команды!");
        }
    }
}
