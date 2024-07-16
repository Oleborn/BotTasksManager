package telegramBot.controllers.output;

import org.glassfish.grizzly.utils.Pair;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
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
        if (currentRow == null || currentRow.size() >= 9) {
            currentRow = new ArrayList<>();
            keyboard.add(currentRow);
        }
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callbackData);
        currentRow.add(inlineKeyboardButton);
        return this;
    }

    public InlineKeyboardBuilder addMenuDaysPerMonths(int buttonsPerRow, int rowsCount, HashMap<Integer, Pair<String, String>> buttonsMap, int lengthMonth) {

        int buttonNumber = 1;

        for (int i = 0; i < rowsCount; i++) {
            currentRow = new ArrayList<>();
            for (int j = 0; j < buttonsPerRow; j++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                if (buttonNumber <= lengthMonth) {
                    button.setText(String.valueOf(buttonsMap.get(buttonNumber).getFirst()));
                    button.setCallbackData(buttonsMap.get(buttonNumber).getSecond());
                    buttonNumber++;
                } else {
                    button.setText(" ");
                    button.setCallbackData("no");
                }
                currentRow.add(button);
            }
            keyboard.add(currentRow);
        }
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
