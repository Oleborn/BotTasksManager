package telegramBot.controllers.filesController.implementations;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.filesController.UserDTOActions;
import telegramBot.controllers.filesController.implementations.parseFiles.userDto.ParseFilesUserDTO;
import telegramBot.logs.LogsConfiguration;
import telegramBot.models.InitUserDTO;
import telegramBot.models.UserDTO;
import telegramBot.models.services.ServiceFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UserDTOActionsImpl implements UserDTOActions {

    private final String pathUserDTOFiles = "src/main/resources/UsersFiles/";

    ServiceFiles serviceFiles = new ServiceFiles();

    @Override
    public void createFileUserDTO(Update update) throws IOException {
        LogsConfiguration.writeLog("Запущен метод createFileUserDTO для " + update.getMessage().getFrom().getUserName());
        if (!serviceFiles.checkExistsDirectory(update.getMessage().getFrom().getId(), pathUserDTOFiles)) {
            Files.createDirectory(Path.of(pathUserDTOFiles + update.getMessage().getFrom().getId()));
            Files.createDirectory(Path.of(pathUserDTOFiles + update.getMessage().getFrom().getId() + "/tasks"));
        }
        if (!serviceFiles.checkExistsFiles(update, pathUserDTOFiles, update.getMessage().getFrom().getUserName() + ".txt")) {
            Files.createFile(Path.of(pathUserDTOFiles + update.getMessage().getFrom().getId() + "/" + update.getMessage().getFrom().getUserName() + ".txt"));
            UserDTO userDTO = new InitUserDTO().firstInitUserDTO(update);
            saveUserDTO(update, userDTO);
        }
    }

    @Override
    public UserDTO loadUserDTO(Update update) {
        return new ParseFilesUserDTO().unparseFileUserDTO(update, pathUserDTOFiles);
    }

    @Override
    public void saveUserDTO(Update update, UserDTO userDTO) {
        new ParseFilesUserDTO().parseUserDTO(update, userDTO, pathUserDTOFiles);
    }

    @Override
    public void deleteUserDTO(Update update) {

    }
}
