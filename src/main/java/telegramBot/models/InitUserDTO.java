package telegramBot.models;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.CommunicationMode;

public class InitUserDTO {

    public UserDTO firstInitUserDTO(Update update){
        return UserDTO.builder()
                .nickName(update.getMessage().getFrom().getUserName())
                .selfName("!!!%%%!!!%%%4")
                .dateOfBirthday("!!!%%%!!!%%%5")
                .id(update.getMessage().getFrom().getId())
                .communicationMode(CommunicationMode.DEFAULT)
                .text("!!!%%%!!!%%%3")
                .gmt(25)
                .listPersonIn(null)
                .listPersonOut(null)
                .build();
    }
}
