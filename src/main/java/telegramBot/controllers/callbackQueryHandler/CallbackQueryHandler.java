package telegramBot.controllers.callbackQueryHandler;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.ParseException;

public interface CallbackQueryHandler {
    public void setCommands(Update update, String command) throws TelegramApiException, IOException, ParseException;
}
