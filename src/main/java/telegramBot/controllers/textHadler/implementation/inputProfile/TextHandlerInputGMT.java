package telegramBot.controllers.textHadler.implementation.inputProfile;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.CommunicationMode;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;
import telegramBot.controllers.output.OutputsMethods;
import telegramBot.controllers.services.ServiceFiles;
import telegramBot.controllers.textHadler.TextHandler;
import telegramBot.models.UserDTO;

public class TextHandlerInputGMT implements TextHandler {

    UserDTOActionsImpl userDTOActions = new UserDTOActionsImpl();

    @Override
    public void textHandlerToCommunicationMode(Update update) {
        if (update.getMessage().getText().startsWith("/") || !update.getMessage().getText().matches("^-?(1[0-2]|[1-9]|0)$|^[+](1[0-2]|[1-9])$")) {
            new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(), "<b>Вы ввели что-то не то!</b> \n\n <i>Можно ввести только цифры от -12 до +12!</i>");
        } else {
            UserDTO userDTO = userDTOActions.loadUserDTO(update);
            userDTO.setGmt(Integer.parseInt(update.getMessage().getText()));
            userDTOActions.saveUserDTO(update, userDTO);
            new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(),
                    "<b>Часовой пояс сохранен!\n\n</b>" +
                            "<i>Можно продолжать работу!</i>");
            new ServiceFiles().setCommunicationMode(update, CommunicationMode.DEFAULT);
        }
    }
}
