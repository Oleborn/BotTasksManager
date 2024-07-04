package telegramBot.controllers.textHadler.implementation.inputTasks;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.controllers.OutputsMethods;
import telegramBot.controllers.textHadler.TextHandler;
import telegramBot.models.services.TasksModelService;

import java.io.IOException;
import java.text.ParseException;

public class TextHandlerInputDate implements TextHandler {
    @Override
    public void textHandlerToCommunicationMode(Update update) throws TelegramApiException, IOException, ParseException {
        if (checkCommand(update)) {
            new TasksModelService().handlerTasksModelGetDate(update);
            new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(),
                    "<b> \uD83D\uDC4C Время уведомления установлено, напоминание сохранено!  </b>\n");
        }
    }
}
