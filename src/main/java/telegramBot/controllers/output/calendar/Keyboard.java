package telegramBot.controllers.output.calendar;

import org.glassfish.grizzly.utils.Pair;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import telegramBot.controllers.filesController.implementations.TasksModelActionsImpl;
import telegramBot.controllers.output.InlineKeyboardBuilder;
import telegramBot.controllers.services.ServiceFiles;
import telegramBot.models.TasksModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

public class Keyboard {
    ServiceFiles serviceFiles = new ServiceFiles();

    public InlineKeyboardMarkup createKeyboard(TasksModel tasksModel) {

        LocalTime localTime = tasksModel.getLocalTime();
        LocalDate localDate = tasksModel.getLocalDate();

        DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("LLLL", new Locale("ru"));

        String year = String.valueOf(localDate.getYear());
        String monthRu = localDate.format(formatterMonth);

        String min = String.valueOf(localTime.getMinute());
        String hour = String.valueOf(localTime.getHour());
        String mins = Integer.parseInt(min) < 10 ? "0" + min : min;
        String hours = Integer.parseInt(hour) < 10 ? "0" + hour : hour;

        HashMap<String, Pair<String, String>> buttons = new HashMap<>();
        buttons.put("month", new Pair<>(("%s").formatted(monthRu), "month"));
        buttons.put("year", new Pair<>(("%s год").formatted(year), "year"));
        buttons.put("minutes", new Pair<>(("%s минут(ы)").formatted(mins), "minutes"));
        buttons.put("hours", new Pair<>(("%s часа(ов)").formatted(hours), "hours"));
        buttons.put("minutesPlus", new Pair<>("↓", "minutesPlus_minutesPlus-task"));
        buttons.put("minutesMinus", new Pair<>("↑", "minutesMinus_minutesMinus-task"));
        buttons.put("hoursPlus", new Pair<>("↓", "hoursPlus_hoursPlus-task"));
        buttons.put("hoursMinus", new Pair<>("↑", "hoursMinus_hoursMinus-task"));
        buttons.put("monthPlus", new Pair<>("→", "monthPlus_monthPlus-task"));
        buttons.put("monthMinus", new Pair<>("←", "monthMinus_monthMinus-task"));
        buttons.put("yearPlus", new Pair<>("→", "yearPlus_yearPlus-task"));
        buttons.put("yearMinus", new Pair<>("←", "yearMinus_yearMinus-task"));

        HashMap<Integer, Pair<String, String>> buttonsDay = new HashMap<>();
        for (int i = 1; i <= localDate.lengthOfMonth(); i++) {
            buttonsDay.put(i, new Pair<>(("%s").formatted(i), ("%s_day-task").formatted(i)));
        }

        return new InlineKeyboardBuilder()
                .addButton(buttons.get("hoursMinus").getFirst(), buttons.get("hoursMinus").getSecond())
                .addButton(buttons.get("minutesMinus").getFirst(), buttons.get("minutesMinus").getSecond())
                .nextRow()
                .addButton(buttons.get("hours").getFirst(), buttons.get("hours").getSecond())
                .addButton(buttons.get("minutes").getFirst(), buttons.get("minutes").getSecond())
                .nextRow()
                .addButton(buttons.get("hoursPlus").getFirst(), buttons.get("hoursPlus").getSecond())
                .addButton(buttons.get("minutesPlus").getFirst(), buttons.get("minutesPlus").getSecond())
                .nextRow()
                .addButton(buttons.get("monthMinus").getFirst(), buttons.get("monthMinus").getSecond())
                .addButton(buttons.get("month").getFirst(), buttons.get("month").getSecond())
                .addButton(buttons.get("monthPlus").getFirst(), buttons.get("monthPlus").getSecond())
                .nextRow()
                .addButton(buttons.get("yearMinus").getFirst(), buttons.get("yearMinus").getSecond())
                .addButton(buttons.get("year").getFirst(), buttons.get("year").getSecond())
                .addButton(buttons.get("yearPlus").getFirst(), buttons.get("yearPlus").getSecond())
                .nextRow()
                .addMenuDaysPerMonths(7, 5, buttonsDay, localDate.lengthOfMonth())
                .build();
    }

    public InlineKeyboardMarkup updateKeyboard(long id, String command) {
        return createKeyboard(handlerCommandForCalendar(id, command));
    }

    private TasksModel loadTaskNotOut(long id) {
        Long nameFile = serviceFiles.checkFilesTasksNoDateOutput(id);// возвращает имя файла в котором DateOutput = NULL
        return new TasksModelActionsImpl().loadTask(id, nameFile); //загружает TasksModel из этого файла
    }

    private TasksModel handlerCommandForCalendar(long id, String command) {
        TasksModel tasksModel = loadTaskNotOut(id);
        switch (command) {
            case "hoursMinus" -> tasksModel.setLocalTime(tasksModel.getLocalTime().minusHours(1));
            case "hoursPlus" -> tasksModel.setLocalTime(tasksModel.getLocalTime().plusHours(1));
            case "minutesMinus" -> tasksModel.setLocalTime(tasksModel.getLocalTime().minusMinutes(1));
            case "minutesPlus" -> tasksModel.setLocalTime(tasksModel.getLocalTime().plusMinutes(1));

            case "monthMinus" -> tasksModel.setLocalDate(tasksModel.getLocalDate().minusMonths(1));
            case "monthPlus" -> tasksModel.setLocalDate(tasksModel.getLocalDate().plusMonths(1));
            case "yearMinus" -> tasksModel.setLocalDate(tasksModel.getLocalDate().minusYears(1));
            case "yearPlus" -> tasksModel.setLocalDate(tasksModel.getLocalDate().plusYears(1));
        }
        new TasksModelActionsImpl().saveTask(id, tasksModel); //сохраняет tasksModel в данный файл
        return tasksModel;
    }

    private TasksModel handlerCommandForCalendarDay(long id, String command) {
        TasksModel tasksModel = loadTaskNotOut(id);
        tasksModel.setLocalDate(tasksModel.getLocalDate().withDayOfMonth(Integer.parseInt(command)));
        new TasksModelActionsImpl().saveTask(id, tasksModel); //сохраняет tasksModel в данный файл
        return tasksModel;
    }

    public String outputDate(long id, String command) {
        TasksModel tasksModel = handlerCommandForCalendarDay(id, command);
        return "%s:%s, %s/%s/%s".formatted(
                tasksModel.getLocalTime().getHour() < 10 ? "0" + tasksModel.getLocalTime().getHour() : String.valueOf(tasksModel.getLocalTime().getHour()),
                tasksModel.getLocalTime().getMinute() < 10 ? "0" + tasksModel.getLocalTime().getMinute() : String.valueOf(tasksModel.getLocalTime().getMinute()),
                tasksModel.getLocalDate().getDayOfMonth() < 10 ? "0" + tasksModel.getLocalDate().getDayOfMonth() : String.valueOf(tasksModel.getLocalDate().getDayOfMonth()),
                tasksModel.getLocalDate().getMonth().getValue() < 10 ? "0" + tasksModel.getLocalDate().getMonth().getValue() : String.valueOf(tasksModel.getLocalDate().getMonth().getValue()),
                String.valueOf(tasksModel.getLocalDate().getYear()).substring(2)
        );
    }
}
