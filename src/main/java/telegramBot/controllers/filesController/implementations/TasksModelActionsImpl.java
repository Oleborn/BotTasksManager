package telegramBot.controllers.filesController.implementations;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.filesController.TasksModelActions;
import telegramBot.controllers.filesController.implementations.parseFiles.userDto.ParseFilesTasks;
import telegramBot.logs.LogsConfiguration;
import telegramBot.models.TasksModel;
import telegramBot.models.UserDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TasksModelActionsImpl implements TasksModelActions {
    private final String pathUserDTOFiles = "src/main/resources/UsersFiles/";

    @Override
    public void createTask(Update update) {
        UserDTO userDTO = new UserDTOActionsImpl().loadUserDTO(update);
        LogsConfiguration.writeLog("Запущен метод createTask для " + update.getMessage().getFrom().getUserName());
        try {
            Files.createFile(Path.of(pathUserDTOFiles + update.getMessage().getFrom().getId() + "/tasks/" + (update.getMessage().getDate() + ((userDTO.getGmt()) * 3600)) + ".txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TasksModel loadTask(Long id, Long createTask) {
        LogsConfiguration.writeLog("Запущен метод loadTask для " + id);
        return new ParseFilesTasks().unparseFileTasks(id, createTask, pathUserDTOFiles);
    }

    @Override
    public void saveTask(Long id, TasksModel tasksModel) {
        LogsConfiguration.writeLog("Запущен метод saveTask для " + id);
        new ParseFilesTasks().parseFileTasks(id, tasksModel, pathUserDTOFiles);
    }

    @Override
    public void deleteTask(Long id, Long nameTask) {
        try {
            Files.delete(Path.of(pathUserDTOFiles + id
                    + "/tasks/" + nameTask + ".txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
