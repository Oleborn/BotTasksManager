package telegramBot.controllers.callbackQueryHandler.implementation;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.callbackQueryHandler.CallbackQueryHandler;
import telegramBot.controllers.callbackQueryHandler.ProfileServices;

public class CallbackQueryHandlerForProfile implements CallbackQueryHandler {

    ProfileServices profileServices = new ProfileServices();

    @Override
    public void setCommands(Update update, String command) {
        switch (command) {
            case "start" -> profileServices.startProfile(update);
            case "edit" -> profileServices.editProfile(update);
            case "delete" -> profileServices.deleteProfile(update);
            case "editName" -> profileServices.editNameProfiles(update);
            case "editGmt" -> profileServices.editGmtProfiles(update);
        }
    }
}
