package telegramBot.controllers.textHadler.implementation.inputTasks;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.CommunicationMode;
import telegramBot.controllers.OutputsMethods;
import telegramBot.controllers.filesController.implementations.TasksModelActionsImpl;
import telegramBot.controllers.textHadler.TextHandler;
import telegramBot.models.TasksModel;
import telegramBot.models.services.ServiceFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TextHandlerUpdateText implements TextHandler {

    ServiceFiles serviceFiles = new ServiceFiles();

    @Override
    public void textHandlerToCommunicationMode(Update update) {
        if (checkCommand(update)) {
            List<Long> listNames = new ArrayList<>();
            String pathTasks = "src/main/resources/UsersFiles/" + update.getMessage().getFrom().getId() + "/tasks/"; // создает путь к таскам конкретного пользователя
            File fileFolder = new File(pathTasks); // получает данные по пути в формате File
            File[] listNameFiles = fileFolder.listFiles(); //сохраняет их в массив файлов
            for (int j = 0; j < Objects.requireNonNull(listNameFiles).length; j++) { // перебираем вксь список имен файлов в формате File
                String[] name = listNameFiles[j].getName().split("\\."); // сплитит от названия таски .txt
                listNames.add(Long.valueOf(name[0])); //сохраняем в список имен в формате Long
            }
            for (Long listName : listNames) {   //перебираем список имен в формате Long
                TasksModel tasksModel = new TasksModelActionsImpl().loadTask(update.getMessage().getFrom().getId(), listName); //перебирая имена в listNames загружает каждый
                if (tasksModel.getStatusUpdated()) {
                    tasksModel.setText(update.getMessage().getText());
                    tasksModel.setStatusUpdated(false);
                    new TasksModelActionsImpl().saveTask(update.getMessage().getFrom().getId(), tasksModel);
                    new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(), "Текст напоминания обновлен и сохранен!");
                    serviceFiles.setCommunicationMode(update, CommunicationMode.DEFAULT);
                }
            }
        }
    }
}
