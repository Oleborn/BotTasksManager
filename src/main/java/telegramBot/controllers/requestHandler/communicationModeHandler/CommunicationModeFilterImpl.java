package telegramBot.controllers.requestHandler.communicationModeHandler;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;
import telegramBot.controllers.services.ServiceFiles;
import telegramBot.controllers.textHadler.implementation.TextHandlerDefault;
import telegramBot.controllers.textHadler.implementation.inputProfile.TextHandlerInputGMT;
import telegramBot.controllers.textHadler.implementation.inputProfile.TextHandlerInputSelfName;
import telegramBot.controllers.textHadler.implementation.inputTasks.TextHandlerInputDate;
import telegramBot.controllers.textHadler.implementation.inputTasks.TextHandlerInputTextTask;
import telegramBot.controllers.textHadler.implementation.inputTasks.TextHandlerUpdateDate;
import telegramBot.controllers.textHadler.implementation.inputTasks.TextHandlerUpdateText;
import telegramBot.models.UserDTO;

public class CommunicationModeFilterImpl implements CommunicationModeFilter {

    @Override
    public void filter(Update update) {
        UserDTO userDTO;
        if (!new ServiceFiles().checkExistsFiles(update, "src/main/resources/UsersFiles/", update.getMessage().getFrom().getUserName() + ".txt")) {
            new TextHandlerDefault().textHandlerToCommunicationMode(update);
        } else {
            userDTO = new UserDTOActionsImpl().loadUserDTO(update);
            switch (userDTO.getCommunicationMode()) {
                case DEFAULT -> {
                    new TextHandlerDefault().textHandlerToCommunicationMode(update);
                }
                case INPUTTEXTTASK -> {
                    new TextHandlerInputTextTask().textHandlerToCommunicationMode(update);
                }
                case INPUTDATE -> new TextHandlerInputDate().textHandlerToCommunicationMode(update);
                case INPUTGMT -> new TextHandlerInputGMT().textHandlerToCommunicationMode(update);
                case UPDATETEXTTASK -> new TextHandlerUpdateText().textHandlerToCommunicationMode(update);
                case UPDATEDATE -> new TextHandlerUpdateDate().textHandlerToCommunicationMode(update);
                case INPUTNAME -> new TextHandlerInputSelfName().textHandlerToCommunicationMode(update);
                //default ->
            }
        }
    }
}
