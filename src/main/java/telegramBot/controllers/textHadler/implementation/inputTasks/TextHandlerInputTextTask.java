package telegramBot.controllers.textHadler.implementation.inputTasks;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.controllers.OutputsMethods;
import telegramBot.controllers.textHadler.TextHandler;
import telegramBot.models.services.TasksModelService;

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
    public void textHandlerToCommunicationMode(Update update) throws TelegramApiException, IOException {
        if (checkCommand(update)) {
            new TasksModelService().handlerTasksModelGetText(update);
            new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(), """
                    <b>Текст напоминания принят!</b>
                                                
                    <b>Теперь введите пожалуйста время и дату!</b>
                                                
                    <i>Обязательно в данном формате - чч:мм, дд/мм/гг                          
                    Где:
                    ч - часы
                    м - минуты
                    д - число
                    м - месяц, в числовом формате
                    г - год </i>
                                                
                    <em><b>Пример - 08:00, 01/06/24</b></em>
                    """);
        }
    }
}
