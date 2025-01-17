package telegramBot.bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegramBot.shedulers.TestScheduler;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws TelegramApiException, IOException, ParseException, InterruptedException {

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot();
        botsApi.registerBot(bot);

        new TestScheduler().timerOfScanner();

    }
}
//TODO
// Возможность делать запрос на контакт и возможность получать от него напоминания, устанавливать ему
// попробовать выделить поток на отсчет 30 сек посекундно для каждого напоминания
// Сделать нормальную обработку Exceptions с выводом в бота
// Набор разных ответов рандомно
// Ограничение вводимых знаков
// Весь текст в коде вынести в отдельный класс и ENUM с возможностью вызова
// Меню на входе - создание профиля- СДЕЛАНО
// Добавить возможность просматривать свой профиль возможность его редактировать- СДЕЛАНО
// ИСПРАВИТЬ - введение текста с переносом строки - СДЕЛАНО
// Для тасков и профиля CRUD с интерфейса бота - СДЕЛАНО
// замутить картинки и отправлять сообщения с картинками - СДЕЛАНО
// НОВЫЕ КНОПКИ - КАЛЕНДАРЬ - СДЕЛАНО
