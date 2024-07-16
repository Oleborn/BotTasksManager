package telegramBot.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TasksModel {
    private Long id;
    private Long dateCreate;
    private String text;
    private Long dateOutput;
    private Boolean statusOutput;
    private Boolean statusUpdated;
    private LocalTime localTime;
    private LocalDate localDate;
}
