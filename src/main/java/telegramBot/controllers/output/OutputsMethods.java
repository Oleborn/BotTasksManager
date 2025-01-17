package telegramBot.controllers.output;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.bot.Bot;
import telegramBot.logs.LogsConfiguration;

import java.util.ArrayList;
import java.util.List;

public class OutputsMethods extends Bot {

    public void outputMessage(Long id, String text) {
        LogsConfiguration.writeLog("Пользователю " + id + ", направлено сообщение с текстом - " + text);
        SendMessage ms = SendMessage.builder()
                .chatId(id)
                .parseMode("HTML")
                .text(text)
                .build();
        try {
            sendApiMethod(ms);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void outputMessage(long id, String text, InlineKeyboardMarkup kb) {
        SendMessage ms = SendMessage.builder()
                .chatId(id)
                .parseMode("HTML").text(text)
                .replyMarkup(kb)
                .build();
        try {
            execute(ms);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void outputMessageWithCapture(Update update, String text, String namePhoto) {
        InputFile inputFile = new InputFile();
        var is = ClassLoader.getSystemResourceAsStream("images/" + namePhoto + ".jpg");
        inputFile.setMedia(is, namePhoto);

        SendPhoto sendPhoto = SendPhoto.builder()
                .photo(inputFile)
                .chatId(update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId())
                .parseMode("HTML")
                .caption(text)
                .build();
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Фото не загрузилось!");
        }
    }

    public void reOutputMessage(long id, int messageid, String text) throws TelegramApiException {
        EditMessageText ms = EditMessageText.builder()
                .chatId(id)
                .messageId(messageid)
                .parseMode("HTML").text(text)
                .build();
        execute(ms);
    }

    public void reOutputMessage(long chatId, int messageId, String text, InlineKeyboardMarkup kb) {
        EditMessageText ms = EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .parseMode("HTML").text(text)
                .replyMarkup(kb)
                .build();
        try {
            execute(ms);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void reOutputInlineKeyboard(long id, int mid, InlineKeyboardMarkup kb) {
        EditMessageReplyMarkup ms = EditMessageReplyMarkup.builder()
                .chatId(id)
                .messageId(mid)
                .replyMarkup(kb)
                .build();
        try {
            execute(ms);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public InlineKeyboardMarkup createButtonInColumn(List<String> list, String nameField, String command) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            var next = createButtonMenuForInline(nameField + list.get(i), list.get(i) + "_" + command);
            keyboard.add(List.of(next));
        }
        markup.setKeyboard(keyboard);
        return markup;
    }

    public InlineKeyboardButton createButtonMenuForInline(String nameButton, String ref) {
        InlineKeyboardButton inl = new InlineKeyboardButton();
        inl.setText(nameButton);
        inl.setCallbackData(ref);
        return inl;
    }
}
