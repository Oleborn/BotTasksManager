package telegramBot.models;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;

import java.io.IOException;

public class InitTaskModel {
    public TasksModel initTaskModel(Update update, Long dateOutput) throws IOException {
        UserDTO userDTO = new UserDTOActionsImpl().loadUserDTO(update);
        return TasksModel.builder()
                .id(update.getMessage().getFrom().getId())
                .dateCreate(update.getMessage().getDate() + (userDTO.getGmt() * 3600L))
                .text(update.getMessage().getText())
                .dateOutput(dateOutput)
                .statusOutput(false)
                .statusUpdated(false)
                .build();
    }
}
