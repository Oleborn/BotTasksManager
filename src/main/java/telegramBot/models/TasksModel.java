package telegramBot.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
