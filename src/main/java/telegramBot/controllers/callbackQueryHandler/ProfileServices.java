package telegramBot.controllers.callbackQueryHandler;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.CommunicationMode;
import telegramBot.controllers.InlineKeyboardBuilder;
import telegramBot.controllers.OutputsMethods;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;
import telegramBot.models.UserDTO;
import telegramBot.models.services.ServiceFiles;

public class ProfileServices {

    public void startProfile(Update update) {
        UserDTO userDTO = new UserDTOActionsImpl().loadUserDTO(update);
        new OutputsMethods().outputMessage(update.getCallbackQuery().getFrom().getId(),
                """
                        <b>Ваш профиль:</b>
                        
                        <i>Имя которым Вы любите себя называть</i> - <b>%s</b>
                        
                        <i>Ваш ID</i> - <b>%d</b>
                        
                        <i>Разница с Москвой</i>  <b>%d</b> <i>часа(ов).</i>
                        """.formatted(userDTO.getSelfName(), userDTO.getId(), userDTO.getGmt()),
                new InlineKeyboardBuilder()
                        .addButton("Изменить свой профиль", "edit-profile")
                        .nextRow()
                        .addButton("Удалить свой профиль", "delete-profile")
                        .build());
    }

    public void editProfile(Update update) {
        new OutputsMethods().reOutputInlineKeyboard(update.getCallbackQuery().getFrom().getId(), update.getCallbackQuery().getMessage().getMessageId(),
                new InlineKeyboardBuilder()
                        .addButton("Изменить имя", "editName-profile")
                        .nextRow()
                        .addButton("Изменить часовой пояс", "editGmt-profile")
                        .build());
    }

    public void deleteProfile(Update update) {
        new OutputsMethods().outputMessage(update.getCallbackQuery().getFrom().getId(),
                "/Бот/ \n\n Да ладно тебе, какой удалить? Оставайся, со мной интересно же! \n\nНе гони давай))) жмакай лучше /start");
    }

    public void editNameProfiles(Update update) {
        UserDTO userDTO = new UserDTOActionsImpl().loadUserDTO(update);
        userDTO.setSelfName("!!!%%%!!!%%%4");
        new UserDTOActionsImpl().saveUserDTO(update, userDTO);
        new ServiceFiles().setCommunicationMode(update, CommunicationMode.INPUTNAME);
        new OutputsMethods().outputMessage(update.getCallbackQuery().getFrom().getId(),
                "Ну вводи новое имя!)) Я записываю!");
    }

    public void editGmtProfiles(Update update) {
        UserDTO userDTO = new UserDTOActionsImpl().loadUserDTO(update);
        userDTO.setGmt(25);
        new UserDTOActionsImpl().saveUserDTO(update, userDTO);
        new ServiceFiles().setCommunicationMode(update, CommunicationMode.INPUTGMT);
        new OutputsMethods().outputMessage(update.getCallbackQuery().getFrom().getId(),
                "Жду новый часовой пояс! \n\n" +
                        "Напоминаю, от -12 до +12 часов!");
    }
}
