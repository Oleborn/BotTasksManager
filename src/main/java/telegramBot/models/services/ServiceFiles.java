package telegramBot.models.services;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.CommunicationMode;
import telegramBot.controllers.filesController.implementations.TasksModelActionsImpl;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;
import telegramBot.models.TasksModel;
import telegramBot.models.UserDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceFiles {
    public Boolean checkExistsDirectory(long id, String pathFiles) {
        return Files.exists(Path.of(pathFiles + id));
    }

    public Boolean checkExistsFiles(Update update, String pathFiles, String nameFile) {
        return Files.isRegularFile(Path.of(pathFiles + update.getMessage().getFrom().getId() + "/" + nameFile));
    }

    public void setCommunicationMode(Update update, CommunicationMode communicationMode) {
        UserDTO userDTO = new UserDTOActionsImpl().loadUserDTO(update);
        userDTO.setCommunicationMode(communicationMode);
        new UserDTOActionsImpl().saveUserDTO(update, userDTO);
    }

    public Long checkFilesTasksNoDateOutput(Long id) throws IOException, ParseException {
        String pathTasks = "src/main/resources/UsersFiles/" + id + "/tasks/"; // создает путь к таскам конкретного пользователя
        Long nameFile = null;
        File fileFolder = new File(pathTasks); // получает данные по пути в формате File
        File[] listNameFiles = fileFolder.listFiles(); //сохраняет их в массив файлов
        List<Long> listNames = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(listNameFiles).length; i++) {
            String[] name = listNameFiles[i].getName().split("\\."); // сплитит от названия таски .txt
            listNames.add(Long.valueOf(name[0]));
        } // перебирает лист и сохраняет в listNames ТОЛЬКО имена тасков
        for (int i = 0; i < listNames.size(); i++) {
            TasksModel tasksModel = new TasksModelActionsImpl().loadTask(id, listNames.get(i)); //перебирая имена в listNames загружает каждый
            if (tasksModel.getDateOutput() == null) { // проверяет getDateOutput на null если да возращает
                nameFile = tasksModel.getDateCreate();
            }
        }
        return nameFile;
    }
}
