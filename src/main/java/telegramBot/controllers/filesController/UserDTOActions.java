package telegramBot.controllers.filesController;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.models.UserDTO;

import java.io.IOException;

public interface UserDTOActions {
    public void createFileUserDTO(Update update) throws IOException;

    public UserDTO loadUserDTO(Update update) throws IOException;

    public void saveUserDTO(Update update, UserDTO userDTO) throws IOException;

    public void deleteUserDTO(Update update);
}
