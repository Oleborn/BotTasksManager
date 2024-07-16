package telegramBot.shedulers;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.controllers.dateHandler.DateOutputHandler;
import telegramBot.logs.LogsConfiguration;

import java.io.IOException;
import java.text.ParseException;

public class TestScheduler {
    public void timerOfScanner() throws InterruptedException, TelegramApiException, IOException, ParseException {
        long count = 1;
        while (true) {
            count++;
            Thread.sleep(30000);
            LogsConfiguration.writeLog("С последнего запуска прошло - " + count * 30 + " секунд!");
            new DateOutputHandler().DateOutputHandler();
        }
    }
}
