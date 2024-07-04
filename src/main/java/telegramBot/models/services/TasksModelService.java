package telegramBot.models.services;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.CommunicationMode;
import telegramBot.controllers.filesController.implementations.TasksModelActionsImpl;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;
import telegramBot.logs.LogsConfiguration;
import telegramBot.models.InitTaskModel;
import telegramBot.models.TasksModel;
import telegramBot.models.UserDTO;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TasksModelService {

    ServiceFiles serviceFiles = new ServiceFiles();

    public void handlerTasksModelGetText(Update update) {
        LogsConfiguration.writeLog("Запущен метод handlerTasksModelGetText для " + update.getMessage().getFrom().getUserName());
        TasksModelActionsImpl tasksModelActions = new TasksModelActionsImpl();
        TasksModel tasksModel = new InitTaskModel().initTaskModel(update, null); //создает модель таски без даты отправки
        tasksModelActions.createTask(update); // создает файл таски с именем из даты этого апдейта
        tasksModelActions.saveTask(update.getMessage().getFrom().getId(), tasksModel); // в него сохраняет дату создания и текст, даты отправки сохраняет как NULL
        serviceFiles.setCommunicationMode(update, CommunicationMode.INPUTDATE);
    }

    public void handlerTasksModelGetDate(Update update){
        UserDTO userDTO = new UserDTOActionsImpl().loadUserDTO(update);
        LogsConfiguration.writeLog("Запущен метод handlerTasksModelGetDate для " + update.getMessage().getFrom().getUserName());
        Long nameFile = serviceFiles.checkFilesTasksNoDateOutput(update.getMessage().getFrom().getId()); // возвращает имя файла в котором DateOutput = NULL
        TasksModelActionsImpl tasksModelActions = new TasksModelActionsImpl();
        TasksModel tasksModel = tasksModelActions.loadTask(update.getMessage().getFrom().getId(), nameFile); //загружает TasksModel из этого файла
        tasksModel.setDateOutput(
                (new ConversionDate().unconversionDate(editDateOutput(update.getMessage().getText())) - userDTO.getGmt() * 3600L) + (4 * 3600L)); // присваевает DateOutput значение из update конвертировав его в Long
        tasksModelActions.saveTask(update.getMessage().getFrom().getId(), tasksModel); //сохраняет tasksModel в данный файл
        serviceFiles.setCommunicationMode(update, CommunicationMode.DEFAULT); // возвращает CommunicationMode в DEFAULT
    }

    public String editDateOutput(String text) {
        String part1 = text.substring(0, 5);
        String part2 = text.substring(5);
        return part1 + ":00" + part2;
    }

    public List<String> getListNamesTasks(Update update) {
        List<String> listNamesTasks = new ArrayList<>();
        List<Long> listNames = new ArrayList<>();
        String pathTasks = "src/main/resources/UsersFiles/" + update.getCallbackQuery().getFrom().getId() + "/tasks/"; // создает путь к таскам конкретного пользователя
        File fileFolder = new File(pathTasks); // получает данные по пути в формате File
        File[] listNameFiles = fileFolder.listFiles(); //сохраняет их в массив файлов
        for (int j = 0; j < Objects.requireNonNull(listNameFiles).length; j++) { // перебираем вксь список имен файлов в формате File
            String[] name = listNameFiles[j].getName().split("\\."); // сплитит от названия таски .txt
            listNames.add(Long.valueOf(name[0])); //сохраняем в список имен в формате Long
        }
        for (Long listName : listNames) {   //перебираем список имен в формате Long
            TasksModel tasksModel = new TasksModelActionsImpl().loadTask(update.getCallbackQuery().getFrom().getId(), listName); //перебирая имена в listNames загружает каждый
            if (tasksModel.getDateOutput() != null) {
                listNamesTasks.add(new ConversionDate().conversionDate(tasksModel.getDateCreate())); //сохраняем в общий список всех дат отправки}
            }
        }
        return listNamesTasks;
    }
}
