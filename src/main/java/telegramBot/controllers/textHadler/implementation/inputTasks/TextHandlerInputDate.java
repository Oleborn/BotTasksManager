package telegramBot.controllers.textHadler.implementation.inputTasks;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.textHadler.TextHandler;

public class TextHandlerInputDate implements TextHandler {
    @Override
    public void textHandlerToCommunicationMode(Update update) {
        if (checkCommand(update, "Введение даты чем то прервано...")) {

        }
    }
}
