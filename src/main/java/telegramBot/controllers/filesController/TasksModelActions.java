package telegramBot.controllers.filesController;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.models.TasksModel;

import java.io.IOException;
import java.text.ParseException;

public interface TasksModelActions {
    public void createTask(Update update) throws IOException;

    public TasksModel loadTask(Long id, Long dateOutput) throws IOException, ParseException;

    public void saveTask(Long id, TasksModel tasksModel) throws IOException;

    void deleteTask(Long id, Long nameTask);
}
