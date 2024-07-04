package telegramBot.controllers;

import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;
import telegramBot.models.services.ServiceFiles;
import telegramBot.models.services.UserDTOService;

public class CommandsHandler {

    @SneakyThrows
    public void setCommandsHandler(Update update) {
        switch (update.getMessage().getText()) {
            case "/start" -> {
                new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(), "<b>Привет!</b>\n\n Я бот который записывает напоминания и присылает их Вам в указанное время! \n\n" +
                        "<em>Чтобы установить напоминание введите - /settask </em>\n\n" +
                        "Я еще нахожусь в начале разработки, поэтому не судите строго \uD83D\uDE09");
                new UserDTOActionsImpl().createFileUserDTO(update);
                new UserDTOService().setSelfName(update);
            }
            case "/settask" -> {
                new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(),
                        "<b>Создание напоминания начато!</b>\n\nВведите сообщение которое я верну Вам в установленное время.\n" +
                                "\n<i>(Учтите, что из-за рассинхронизации серверного и локального времени, уведомления могут приходить раньше или позже на 3-7 минут. Редко время может быть больше.)</i>");
                new ServiceFiles().setCommunicationMode(update, CommunicationMode.INPUTTEXTTASK);
            }
            case "/profile" -> {
                new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(), "<i><b>Вам доступны:</b></i>",
                        new InlineKeyboardBuilder()
                                .addButton("Профиль", "start-profile")
                                .nextRow()
                                .addButton("Напоминания", update.getMessage().getFrom().getId() + "_start-task")
                                .build());

            }
        }
    }
}
