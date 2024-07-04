package telegramBot.controllers.requestHandler;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.ParseException;

public interface RequestHandler {
    public void requestHandler(Update update) throws TelegramApiException, IOException, ParseException, InterruptedException;
}
