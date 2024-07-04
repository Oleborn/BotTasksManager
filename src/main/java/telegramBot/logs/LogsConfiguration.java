package telegramBot.logs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogsConfiguration {
    private static final String FILE_PATH = "src/logs/log.txt"; // Путь к файлу, куда будут записываться данные
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH.mm.ss, dd/MM/yy");

    private static long count = 0;

    public static void writeLog(String message) {
        count++;
        if (count > 15000) {
            deleteLog();
            count = 0;
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
                writer.write(count + ". " + timestamp + " - " + message);
                writer.newLine(); // Добавляет новую строку после записи сообщения
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void deleteLog() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
