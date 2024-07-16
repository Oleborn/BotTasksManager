package telegramBot.controllers.dateHandler;

import telegramBot.controllers.filesController.implementations.TasksModelActionsImpl;
import telegramBot.controllers.output.OutputsMethods;
import telegramBot.controllers.services.ConversionDate;
import telegramBot.controllers.services.ScannersFiles;
import telegramBot.models.TasksModel;

import java.time.Instant;
import java.util.List;

public class DateOutputHandler {
    public void dateOutputHandler() {
        List<TasksModel> allTasksModels = new ScannersFiles().scannerTasksForSearchDateOutput(); //создает список всех имеющихся неотправленных тасков
        if (!allTasksModels.isEmpty()) { // проверка на отсутвие неотправленных
            long count = 0;
            for (TasksModel tasksModel : allTasksModels) { // перебор всех
                long unixTime = Instant.now().getEpochSecond(); // возвращает время в юникс формате по гринвичу
                if (tasksModel.getDateOutput() <= (unixTime + 30)) { // ключевое место!!! Если время отправки таски меньше или равно текущему времени +30 секунд
                    new OutputsMethods().outputMessage(tasksModel.getId(),
                            "<em>/БОТ/</em> \n<b>Возвращаю Вам напоминание, созданное: </b>" + new ConversionDate().conversionDate(tasksModel.getDateCreate()) + "\n\n" +
                                    "<b>Текст напоминания:</b> \n" +
                                    "\"" + tasksModel.getText() + "\".");
                    tasksModel.setStatusOutput(true);
                    new TasksModelActionsImpl().saveTask(tasksModel.getId(), tasksModel);
                } else
                    count++;
            }
            System.out.println("Количество еще не отправленных напоминаний - " + count);
        } else System.out.println("Не отправленные напоминания отсутствуют!");
    }
}

