package telegramBot.models;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;

import java.time.LocalDate;
import java.time.LocalTime;

public class InitTaskModel {
    public TasksModel initTaskModel(Update update, Long dateOutput) {
        UserDTO userDTO = new UserDTOActionsImpl().loadUserDTO(update);
        return TasksModel.builder()
                .id(update.getMessage().getFrom().getId())
                .dateCreate(update.getMessage().getDate() + (userDTO.getGmt() * 3600L))
                .text(update.getMessage().getText())
                .dateOutput(dateOutput)
                .statusOutput(false)
                .statusUpdated(false)
                .localTime(LocalTime.now())
                .localDate(LocalDate.now())
                .build();
    }
}
