package telegramBot.controllers.textHadler.implementation.inputTasks;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.controllers.output.OutputsMethods;
import telegramBot.controllers.output.calendar.Keyboard;
import telegramBot.controllers.services.TasksModelService;
import telegramBot.controllers.textHadler.TextHandler;

import java.io.IOException;

public class TextHandlerInputTextTask implements TextHandler {
    /**
     * Метод который принимает Update и сохраняет текст в TasksModel, отправляет уведомление, переключает на INPUTDATE
     *
     * @param update
     * @throws TelegramApiException
     * @throws IOException
     */
    @Override
    public void textHandlerToCommunicationMode(Update update) {
        if (checkCommand(update, "Введение напоминания чем то прервано...")) {
            new TasksModelService().handlerTasksModelGetText(update);
            new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(), "<b>Текст напоминания принят!</b>");
            new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(), """                
                    <b>Выберите пожалуйста дату отправки!</b>
                    """, new Keyboard().updateKeyboard(update.getMessage().getFrom().getId(), ""));
        }
    }
}
