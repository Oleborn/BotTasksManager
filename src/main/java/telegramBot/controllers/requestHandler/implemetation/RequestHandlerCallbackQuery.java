package telegramBot.controllers.requestHandler.implemetation;

import org.glassfish.grizzly.utils.Pair;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.controllers.callbackQueryHandler.implementation.CallbackQueryHandlerForProfile;
import telegramBot.controllers.callbackQueryHandler.implementation.CallbackQueryHandlerForTasks;
import telegramBot.controllers.requestHandler.RequestHandler;

import java.io.IOException;
import java.text.ParseException;

public class RequestHandlerCallbackQuery implements RequestHandler {
    @Override
    public void requestHandler(Update update) throws TelegramApiException, IOException, ParseException {
        Pair<String, String> pair = handlerCommands(update.getCallbackQuery().getData(), "-");
        switch (pair.getSecond()) {
            case "profile" -> new CallbackQueryHandlerForProfile().setCommands(update, pair.getFirst());
            case "task" -> new CallbackQueryHandlerForTasks().setCommands(update, pair.getFirst());
        }
    }

    public Pair<String, String> handlerCommands(String command, String symbol) {
        Pair<String, String> data = new Pair<>();
        String[] name = command.split(symbol);
        data.setFirst(name[0]);
        data.setSecond(name[1]);
        return data;
    }
}