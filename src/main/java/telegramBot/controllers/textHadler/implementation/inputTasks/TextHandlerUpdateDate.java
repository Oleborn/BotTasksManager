package telegramBot.controllers.textHadler.implementation.inputTasks;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.CommunicationMode;
import telegramBot.controllers.OutputsMethods;
import telegramBot.controllers.filesController.implementations.TasksModelActionsImpl;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;
import telegramBot.controllers.textHadler.TextHandler;
import telegramBot.models.TasksModel;
import telegramBot.models.UserDTO;
import telegramBot.models.services.ConversionDate;
import telegramBot.models.services.ServiceFiles;
import telegramBot.models.services.TasksModelService;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextHandlerUpdateDate implements TextHandler {

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
                if (tasksModel.getStatusUpdated()) { // проверка на статус таски - открыто для изменений
                    UserDTO userDTO = new UserDTOActionsImpl().loadUserDTO(update); // загружаем профиль пользователя для получения GMT
                    tasksModel.setDateOutput((new ConversionDate().unconversionDate(new TasksModelService().editDateOutput((
                            update.getMessage().getText()))) - userDTO.getGmt() * 3600L) + (4 * 3600L)); //устанавливаем время отправки, с учетом перевода из String в long, убирания .00(секунд) и расчета часового пояса сервера
                    tasksModel.setStatusUpdated(false); //устанавливаем статус исправления таски на фолс(закрыто для исправлени)
                    if (checkDate(update, new ConversionDate().unconversionDate(
                            new TasksModelService().editDateOutput(update.getMessage().getText())))) {
                        tasksModel.setStatusOutput(false);
                    } // проверяем что дата отправки после текущей тогда статус отправки меняется на "Не отправлено"
                    new TasksModelActionsImpl().saveTask(update.getMessage().getFrom().getId(), tasksModel); // сохранение таски
                    new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(), "Время отправки обновлено и сохранено!");
                    serviceFiles.setCommunicationMode(update, CommunicationMode.DEFAULT); // перевод CommunicationMode в дефаулт
                }
            }
        }
    }

    public Boolean checkDate(Update update, long date) {
        UserDTO userDTO = new UserDTOActionsImpl().loadUserDTO(update);
        long unixTime = Instant.now().getEpochSecond();
        long newDate = (date - (userDTO.getGmt() * 3600L)) + (4 * 3600L);
        return newDate > unixTime;
    }

    public String editDateOutputMinusNulls(String text) {
        String part1 = text.substring(0, 5);
        String part2 = text.substring(8);
        return part1 + part2;
    }
}
