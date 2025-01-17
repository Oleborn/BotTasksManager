package telegramBot.controllers.textHadler.implementation.inputProfile;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.CommunicationMode;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;
import telegramBot.controllers.output.OutputsMethods;
import telegramBot.controllers.services.ServiceFiles;
import telegramBot.controllers.services.UserDTOService;
import telegramBot.controllers.textHadler.TextHandler;
import telegramBot.models.UserDTO;

public class TextHandlerInputSelfName implements TextHandler {
    UserDTOActionsImpl userDTOActions = new UserDTOActionsImpl();

    @Override
    public void textHandlerToCommunicationMode(Update update) {
        if (checkCommand(update, "Введение имени чем то прервано...")) {
            UserDTO userDTO = userDTOActions.loadUserDTO(update);
            userDTO.setSelfName(update.getMessage().getText());
            userDTOActions.saveUserDTO(update, userDTO);
            new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(),
                    "<b>Я запомнил твое имя, " + userDTO.getSelfName() + "!\uD83D\uDE09</b>");
            new ServiceFiles().setCommunicationMode(update, CommunicationMode.DEFAULT);
            new UserDTOService().setGMT(update);
        }
    }
}
