package telegramBot.controllers.requestHandler.communicationModeHandler;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.ParseException;

public interface CommunicationModeFilter {
    public void filter(Update update) throws IOException, TelegramApiException, ParseException;
}
