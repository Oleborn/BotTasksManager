package telegramBot.bot.configLoader;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class ConfigProperties {
    private String token;
    private String nameBot;
}
