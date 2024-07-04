package telegramBot.models.services;

import telegramBot.logs.LogsConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConversionDate {
    private final String pattern = "HH:mm:ss, dd/MM/yy";

    public String conversionDate(Long date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date * 1000L);
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(calendar.getTime());
        } else {
            return null;
        }
    }

    public Long unconversionDate(String date) {
        LogsConfiguration.writeLog("Запущен метод unconversionDate по дате " + date);
        if (date != null && !date.equals("null")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            //dateFormat.setTimeZone(TimeZone.getDefault());
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(dateFormat.parse(date));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return calendar.getTimeInMillis() / 1000L;
        } else {
            return null;
        }
    }

}
