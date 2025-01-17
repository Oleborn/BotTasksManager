package telegramBot.controllers.callbackQueryHandler;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.CommunicationMode;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;
import telegramBot.controllers.output.InlineKeyboardBuilder;
import telegramBot.controllers.output.OutputsMethods;
import telegramBot.controllers.services.ServiceFiles;
import telegramBot.models.UserDTO;

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
                """
                        <b>/Бот/</b>
                                        
                        <em>Да ладно тебе, какой удалить? </em>
                        <em>Оставайся, со мной интересно же!😏</em>
                                        
                        <i>Не гони давай,  🫵  ты нужен здесь!</i>
                                        
                        <i><b>Жмакай лучше /start!</b></i>
                        """);
    }

    public void editNameProfiles(Update update) {
        UserDTO userDTO = new UserDTOActionsImpl().loadUserDTO(update);
        userDTO.setSelfName("!!!%%%!!!%%%4");
        new UserDTOActionsImpl().saveUserDTO(update, userDTO);
        new ServiceFiles().setCommunicationMode(update, CommunicationMode.INPUTNAME);
        new OutputsMethods().outputMessage(update.getCallbackQuery().getFrom().getId(),
                """
                        <em>Ну вводи свое новое имя!😉 
                                            
                         Я записываю!</em>
                            """);
    }

    public void editGmtProfiles(Update update) {
        UserDTO userDTO = new UserDTOActionsImpl().loadUserDTO(update);
        userDTO.setGmt(25);
        new UserDTOActionsImpl().saveUserDTO(update, userDTO);
        new ServiceFiles().setCommunicationMode(update, CommunicationMode.INPUTGMT);
        new OutputsMethods().outputMessage(update.getCallbackQuery().getFrom().getId(),
                "Жду новый часовой пояс! \n\n" +
                        "Напоминаю, что вводим значения от -12 до +12 часов!");
    }
}
