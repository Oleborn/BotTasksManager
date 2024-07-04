package telegramBot.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import telegramBot.controllers.CommunicationMode;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String nickName;
    private String selfName;
    private String dateOfBirthday;
    private Long id;
    private CommunicationMode communicationMode;
    private String text;
    private int gmt;
    private List<Long> listPersonIn;
    private List<Long> listPersonOut;
}
