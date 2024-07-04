package telegramBot.controllers.callbackQueryHandler.implementation;

import org.glassfish.grizzly.utils.Pair;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.controllers.CommunicationMode;
import telegramBot.controllers.InlineKeyboardBuilder;
import telegramBot.controllers.OutputsMethods;
import telegramBot.controllers.callbackQueryHandler.CallbackQueryHandler;
import telegramBot.controllers.filesController.implementations.TasksModelActionsImpl;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;
import telegramBot.controllers.requestHandler.implemetation.RequestHandlerCallbackQuery;
import telegramBot.controllers.textHadler.implementation.inputTasks.TextHandlerUpdateDate;
import telegramBot.models.TasksModel;
import telegramBot.models.UserDTO;
import telegramBot.models.services.ConversionDate;
import telegramBot.models.services.ServiceFiles;
import telegramBot.models.services.TasksModelService;

public class CallbackQueryHandlerForTasks implements CallbackQueryHandler {
    @Override
    public void setCommands(Update update, String command) throws TelegramApiException {
        Pair<String, String> pair = new RequestHandlerCallbackQuery().handlerCommands(command, "_");
        switch (pair.getSecond()) {
            case "start" -> new OutputsMethods().reOutputInlineKeyboard(update.getCallbackQuery().getFrom().getId(), update.getCallbackQuery().getMessage().getMessageId(), new InlineKeyboardBuilder()
                    .addButton("Посмотреть сохраненные напоминания", "_view-task")
                    .build());
            case "view" ->
                    new OutputsMethods().reOutputInlineKeyboard(update.getCallbackQuery().getFrom().getId(), update.getCallbackQuery().getMessage().getMessageId(),
                            new OutputsMethods().createButtonInColumn(new TasksModelService().getListNamesTasks(update), "Напоминание от: ", "getTasks-task"));
            case "getTasks" -> setCommandsToTasks(update, pair.getFirst());
            case "edit" -> new OutputsMethods().reOutputInlineKeyboard(update.getCallbackQuery().getFrom().getId(), update.getCallbackQuery().getMessage().getMessageId(),
                    new InlineKeyboardBuilder()
                    .addButton("Изменить текст напоминания", pair.getFirst() +"_editText-task").nextRow().addButton("Изменить время отправки", pair.getFirst() +"_editTime-task")
                    .build());
            case "delete" -> {
                new TasksModelActionsImpl().deleteTask(update.getCallbackQuery().getFrom().getId(), Long.valueOf(pair.getFirst()));
                new OutputsMethods().outputMessage(update.getCallbackQuery().getFrom().getId(), "Напоминание созданное в "+
                        new ConversionDate().conversionDate(Long.valueOf(pair.getFirst())) +" удалено!");
            }
            case "editText" -> updateText(update, pair.getFirst());
            case "editTime" -> updateDate(update, pair.getFirst());
        }
    }
    public void setCommandsToTasks(Update update, String nameTask){
        UserDTO userDTO = new UserDTOActionsImpl().loadUserDTO(update);
        TasksModel tasksModel = new TasksModelActionsImpl().loadTask(update.getCallbackQuery().getFrom().getId(), new ConversionDate().unconversionDate(nameTask));
        String status = tasksModel.getStatusOutput() ? "Отправлено" : "Не отправлено";
        new OutputsMethods().outputMessage(update.getCallbackQuery().getFrom().getId(),
                "Напоминание созданное: " +new ConversionDate().conversionDate(tasksModel.getDateCreate())+"\n\n"+
                    "Текст напоминания: "+ tasksModel.getText()+"\n\n"+
                        "Запланированная дата отправления: "
                        + new TextHandlerUpdateDate().editDateOutputMinusNulls(new ConversionDate().conversionDate(((tasksModel.getDateOutput()-(4*3600L))+(userDTO.getGmt()* 3600L))))+"\n\n"+
                        "Статус отправки: " + status,
                new InlineKeyboardBuilder().addButton("Изменить напоминание", tasksModel.getDateCreate()+"_edit"+"-task")
                        .addButton("Удалить напоминание", tasksModel.getDateCreate()+"_delete-task").build());
    }
    private void updateText(Update update, String nameTasks){
        new ServiceFiles().setCommunicationMode(update, CommunicationMode.UPDATETEXTTASK);
        TasksModel tasksModel =  new TasksModelActionsImpl().loadTask(update.getCallbackQuery().getFrom().getId(), Long.valueOf(nameTasks));
        tasksModel.setStatusUpdated(true);
        System.out.println(tasksModel);
        new TasksModelActionsImpl().saveTask(update.getCallbackQuery().getFrom().getId(), tasksModel);
        new OutputsMethods().outputMessage(update.getCallbackQuery().getFrom().getId(), "Введите измененный текст!");
    }
    private void updateDate(Update update, String nameTasks){
        new ServiceFiles().setCommunicationMode(update, CommunicationMode.UPDATEDATE);
        TasksModel tasksModel =  new TasksModelActionsImpl().loadTask(update.getCallbackQuery().getFrom().getId(), Long.valueOf(nameTasks));
        tasksModel.setStatusUpdated(true);
        new TasksModelActionsImpl().saveTask(update.getCallbackQuery().getFrom().getId(), tasksModel);
        new OutputsMethods().outputMessage(update.getCallbackQuery().getFrom().getId(), "Введите новое время!");
    }
}
