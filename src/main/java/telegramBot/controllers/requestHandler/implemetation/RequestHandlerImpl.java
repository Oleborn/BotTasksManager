package telegramBot.controllers.requestHandler.implemetation;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.requestHandler.RequestHandler;
import telegramBot.logs.LogsConfiguration;

public class RequestHandlerImpl implements RequestHandler {
    @Override
    public void requestHandler(Update update) {
        if (update.hasMessage()) {
            LogsConfiguration.writeLog("Поступил message от " + update.getMessage().getFrom().getUserName() +
                    "(id - " + update.getMessage().getFrom().getId() + ") " + " с текстом - " + update.getMessage().getText());

            new RequestHandlerMessage().requestHandler(update);
        } else if (update.hasCallbackQuery()) {
            LogsConfiguration.writeLog("Поступил command от " + update.getCallbackQuery().getFrom().getUserName() +
                    "(id - " + update.getCallbackQuery().getFrom().getId() + ") " + " с текстом - " + update.getCallbackQuery().getData());

            new RequestHandlerCallbackQuery().requestHandler(update);
        } else {
            throw new IllegalArgumentException("Необрабатываемый тип сообщения");
        }
    }
}
