package telegramBot.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.bot.configLoader.ConfigLoader;
import telegramBot.bot.configLoader.ConfigProperties;
import telegramBot.controllers.requestHandler.implemetation.RequestHandlerImpl;


public class Bot extends TelegramLongPollingBot {
    ConfigLoader configLoader = new ConfigLoader();
    ConfigProperties configProperties = configLoader.loadConfig("config.properties");

    @Override
    public String getBotUsername() {
        return configProperties.getNameBot();
    }

    @Override
    public String getBotToken() {
        return configProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        new RequestHandlerImpl().requestHandler(update);
    }
}
