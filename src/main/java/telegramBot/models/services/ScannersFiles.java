package telegramBot.models.services;

import telegramBot.controllers.filesController.implementations.TasksModelActionsImpl;
import telegramBot.models.TasksModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScannersFiles {

    public List<Long> scannerIdUsers() throws IOException {

        List<Long> listNamesUsers = new ArrayList<>();
        String userFilesPath = "src/main/resources/UsersFiles/";
        DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(userFilesPath));
        for (Path entry : stream) {
            if (Files.isDirectory(entry)) {
                String name = entry.getFileName().toString();
                listNamesUsers.add(Long.valueOf(name));
            }
        }
        return listNamesUsers;
    }

    public List<TasksModel> scannerTasksForSearchDateOutput() throws IOException, ParseException {
        List<TasksModel> allTasksModel = new ArrayList<>();
        List<Long> listIdUsers = scannerIdUsers();
        for (Long listIdUser : listIdUsers) {
            List<Long> listNames = new ArrayList<>();
            String pathTasks = "src/main/resources/UsersFiles/" + listIdUser + "/tasks/"; // создает путь к таскам конкретного пользователя
            File fileFolder = new File(pathTasks); // получает данные по пути в формате File
            File[] listNameFiles = fileFolder.listFiles(); //сохраняет их в массив файлов
            for (int j = 0; j < Objects.requireNonNull(listNameFiles).length; j++) { // перебираем вксь список имен файлов в формате File
                String[] name = listNameFiles[j].getName().split("\\."); // сплитит от названия таски .txt
                listNames.add(Long.valueOf(name[0])); //сохраняем в список имен в формате Long
            }
            for (Long listName : listNames) {   //перебираем список имен в формате Long
                TasksModel tasksModel = new TasksModelActionsImpl().loadTask(listIdUser, listName); //перебирая имена в listNames загружает каждый
                if (tasksModel.getDateOutput() != null && !tasksModel.getStatusOutput()) {
                    allTasksModel.add(tasksModel); //сохраняем в общий список всех дат отправки}
                }
            }
        }
        return allTasksModel;
    }
}
