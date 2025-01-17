package telegramBot.controllers.filesController.implementations.parseFiles.userDto;

import telegramBot.controllers.services.ConversionDate;
import telegramBot.logs.LogsConfiguration;
import telegramBot.models.TasksModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class ParseFilesTasks {
    ConversionDate conversionDate = new ConversionDate();

    public void parseFileTasks(Long id, TasksModel tasksModel, String path) {
        LogsConfiguration.writeLog("Запущен метод parseFileTasks для " + id);
        String dateRec = conversionDate.conversionDate(tasksModel.getDateCreate());
        String dateOut = conversionDate.conversionDate(tasksModel.getDateOutput());
        try {
            Files.writeString(Path.of(path + id + "/tasks/" + tasksModel.getDateCreate() + ".txt"),
                    "Id☼" + id + "\n"
                            + "Date_Of_Recording☼" + dateRec + "\n"
                            + "Date_Output☼" + dateOut + "\n"
                            + "StatusOutput☼" + tasksModel.getStatusOutput() + "\n"
                            + "StatusUpdated☼" + tasksModel.getStatusUpdated() + "\n"
                            + "Text_reminder☼" + tasksModel.getText() + "\n"
                            + "LocalTime☼" + tasksModel.getLocalTime() + "\n"
                            + "LocalDate☼" + tasksModel.getLocalDate() + "\n"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TasksModel unparseFileTasks(Long id, Long createTask, String path) {
        LogsConfiguration.writeLog("Запущен метод unparseFileTasks для " + id + " по номеру таски " + createTask);
        TasksModel tasksModel = new TasksModel();
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(path + id + "/tasks/" + createTask + ".txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, String> entityMap = new HashMap<>();
        String[] parts = new String[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("☼")) {
                parts[i] = lines.get(i);
                String[] download = parts[i].split("☼");
                entityMap.put(download[0], download[1]);
            } else {
                String text = entityMap.get("Text_reminder");
                String newText = text + "\n" + lines.get(i);
                entityMap.put("Text_reminder", newText);
            }
        }

        tasksModel.setId(Long.valueOf(entityMap.get("Id")));
        tasksModel.setDateCreate(new ConversionDate().unconversionDate(entityMap.get("Date_Of_Recording")));
        tasksModel.setText(entityMap.get("Text_reminder"));
        tasksModel.setDateOutput(new ConversionDate().unconversionDate(entityMap.get("Date_Output")));
        tasksModel.setStatusOutput(Boolean.valueOf(entityMap.get("StatusOutput")));
        tasksModel.setStatusUpdated(Boolean.valueOf(entityMap.get("StatusUpdated")));
        tasksModel.setLocalTime(LocalTime.parse(entityMap.get("LocalTime")));
        tasksModel.setLocalDate(LocalDate.parse((entityMap.get("LocalDate"))));
        return tasksModel;
    }
}
