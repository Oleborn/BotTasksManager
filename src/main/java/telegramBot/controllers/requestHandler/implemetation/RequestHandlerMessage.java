package telegramBot.controllers.requestHandler.implemetation;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.requestHandler.RequestHandler;
import telegramBot.controllers.requestHandler.communicationModeHandler.CommunicationModeFilterImpl;


public class RequestHandlerMessage implements RequestHandler {
    @Override
    public void requestHandler(Update update) {
        new CommunicationModeFilterImpl().filter(update);
    }
}
