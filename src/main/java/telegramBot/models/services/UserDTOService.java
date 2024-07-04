package telegramBot.models.services;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.CommunicationMode;
import telegramBot.controllers.OutputsMethods;
import telegramBot.controllers.filesController.implementations.UserDTOActionsImpl;

public class UserDTOService {

    public void setGMT(Update update) {
        if (new UserDTOActionsImpl().loadUserDTO(update).getGmt() == 25) {
            new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(), """
                    <b>Кажется я не знаю ваш часовой пояс!</b>

                    <i>Следущим сообщением напишите мне его в формате от -12 до +12 к МСК!</i>""");
            new ServiceFiles().setCommunicationMode(update, CommunicationMode.INPUTGMT);
        }
    }

    public void setSelfName(Update update) {
        if (new UserDTOActionsImpl().loadUserDTO(update).getSelfName().equals("!!!%%%!!!%%%4")) {
            new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(), """
                    <b>Ну, давайте знакомиться?</b>           
                                        
                    <i>Напишите как мне Вас называть?</i>""");
            new ServiceFiles().setCommunicationMode(update, CommunicationMode.INPUTNAME);
        }
    }

    public void setDateOfBirthday(Update update) {
        new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(),
                """
                        <b></b>           
                                            
                        <i>Напишите как мне Вас называть?</i>""");
        new ServiceFiles().setCommunicationMode(update, CommunicationMode.INPUTNAME);
    }

    public void setSelfInfo(Update update) {
        new OutputsMethods().outputMessage(update.getMessage().getFrom().getId(),
                """
                        <b>Расскажите что-то о себе?</b>           
                                        
                        <i>Коротким сообщением пришлите немного информации о себе)</i>""");
        new ServiceFiles().setCommunicationMode(update, CommunicationMode.INPUTNAME);
    }
}
