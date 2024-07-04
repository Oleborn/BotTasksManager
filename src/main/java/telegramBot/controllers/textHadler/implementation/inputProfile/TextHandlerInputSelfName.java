package telegramBot.controllers.textHadler.implementation.inputProfile;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.CommunicationMode;
import telegramBot.controllers.OutputsMethods;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;
import telegramBot.controllers.textHadler.TextHandler;
import telegramBot.models.UserDTO;
import telegramBot.models.services.ServiceFiles;
import telegramBot.models.services.UserDTOService;

public class TextHandlerInputSelfName implements TextHandler {
    UserDTOActionsImpl userDTOActions = new UserDTOActionsImpl();

    @Override
    public void textHandlerToCommunicationMode(Update update) {
        if (checkCommand(update)) {
            UserDTO userDTO = userDTOActions.loadUserDTO(update);
            userDTO.setSelfName(update.getMessage().getText());
            userDTOActions.saveUserDTO(update, userDTO);
            new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(),
                    "<b>Я запомнил Ваше имя, " + userDTO.getSelfName() + "!\uD83D\uDE09</b>");
            new ServiceFiles().setCommunicationMode(update, CommunicationMode.DEFAULT);
            new UserDTOService().setGMT(update);
        }
    }
}
