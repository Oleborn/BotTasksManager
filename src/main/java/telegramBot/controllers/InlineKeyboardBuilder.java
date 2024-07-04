package telegramBot.controllers;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


/**
 * This class created by GPTChat
 */
public class InlineKeyboardBuilder {
    private List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
    private List<InlineKeyboardButton> currentRow = null;

    public InlineKeyboardBuilder() {
    }

    // Добавить кнопку в текущую строку
    public InlineKeyboardBuilder addButton(String text, String callbackData) {
        if (currentRow == null || currentRow.size() >= 5) {
            currentRow = new ArrayList<>();
            keyboard.add(currentRow);
        }
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callbackData);
        currentRow.add(inlineKeyboardButton);
        return this;
    }

    // Закончить текущую строку и начать новую
    public InlineKeyboardBuilder nextRow() {
        currentRow = new ArrayList<>();
        keyboard.add(currentRow);
        return this;
    }

    // Собрать и вернуть готовую клавиатуру
    public InlineKeyboardMarkup build() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        return markup;
    }
}
